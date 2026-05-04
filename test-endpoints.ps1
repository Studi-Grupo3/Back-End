# Backend Integration Test Script
# Tests all major endpoints against a running backend at localhost:8080
# Requires: DataLoader seeded data (profile=dev)

$baseUrl = "http://localhost:8080/api"
$passed = 0; $failed = 0

function Test-Endpoint {
    param([string]$Name, [scriptblock]$Block)
    try {
        $result = & $Block
        if ($result -eq $false) { throw "Assertion failed" }
        Write-Host "[PASS] $Name" -ForegroundColor Green
        $script:passed++
    } catch {
        Write-Host "[FAIL] $Name - $($_.Exception.Message)" -ForegroundColor Red
        $script:failed++
    }
}

# --- Health ---
Test-Endpoint "GET /health" {
    $r = Invoke-RestMethod "$baseUrl/health"
    $r.status -eq "UP"
}

# --- Student Registration ---
function New-CPF {
    $n = 0..8 | ForEach-Object { Get-Random -Minimum 0 -Maximum 10 }
    $d1 = 0; for($i=0;$i-lt9;$i++){$d1+=$n[$i]*(10-$i)}; $d1=11-($d1%11); if($d1-ge10){$d1=0}
    $n += $d1
    $d2 = 0; for($i=0;$i-lt10;$i++){$d2+=$n[$i]*(11-$i)}; $d2=11-($d2%11); if($d2-ge10){$d2=0}
    $n += $d2
    return ($n -join '')
}

$testEmail = "test_$(Get-Random)@studi.com"
$testCpf = New-CPF
Test-Endpoint "POST /students (register)" {
    $body = @{name="Test Student";email=$testEmail;cpf=$testCpf;password="TestPass123"} | ConvertTo-Json
    $r = Invoke-RestMethod "$baseUrl/students" -Method POST -Body $body -ContentType "application/json"
    $r.id -gt 0
}

# --- Login (seeded student) ---
$studentToken = $null
Test-Endpoint "POST /auths/login (student)" {
    $r = Invoke-RestMethod "$baseUrl/auths/login" -Method POST -Body '{"email":"matheus@gmail.com","password":"senha123"}' -ContentType "application/json"
    $script:studentToken = $r.token
    $r.role -eq "STUDENT"
}

# --- Login (seeded teacher) ---
$teacherToken = $null
Test-Endpoint "POST /auths/login (teacher)" {
    $r = Invoke-RestMethod "$baseUrl/auths/login" -Method POST -Body '{"email":"carlos.prof@gmail.com","password":"senha123"}' -ContentType "application/json"
    $script:teacherToken = $r.token
    $r.role -eq "TEACHER"
}

# --- Admin login ---
Test-Endpoint "POST /auths/login (admin)" {
    $r = Invoke-RestMethod "$baseUrl/auths/login" -Method POST -Body '{"email":"admin@exemplo.com","password":"password"}' -ContentType "application/json"
    $r.role -eq "ADMIN"
}

$sh = @{Authorization="Bearer $studentToken"}
$th = @{Authorization="Bearer $teacherToken"}

# --- List teachers (public) ---
Test-Endpoint "GET /teachers (paginated)" {
    $r = Invoke-RestMethod "$baseUrl/teachers?page=0&size=5"
    $r.totalElements -ge 10
}

# --- Get student by ID ---
Test-Endpoint "GET /students/1" {
    $r = Invoke-RestMethod "$baseUrl/students/1" -Headers $sh
    $r.name -eq "Matheus Alves"
}

# --- Get teacher by ID ---
Test-Endpoint "GET /teachers/2" {
    $r = Invoke-RestMethod "$baseUrl/teachers/2" -Headers $sh
    $r.name.Length -gt 0
}

# --- Create appointment ---
$apptId = $null
Test-Endpoint "POST /appointments" {
    $body = '{"idStudent":1,"idTeacher":2,"dateTime":"2026-07-01T10:00:00","subject":"MATHEMATICS","status":"SCHEDULED","lessonDuration":60,"totalValue":75.5,"paymentStatus":"PENDING","location":"Online"}'
    $r = Invoke-RestMethod "$baseUrl/appointments" -Method POST -Body $body -ContentType "application/json" -Headers $sh
    $script:apptId = $r.id
    $r.status -eq "SCHEDULED"
}

# --- Get appointments ---
Test-Endpoint "GET /appointments (student)" {
    $r = Invoke-RestMethod "$baseUrl/appointments/student/1" -Headers $sh
    $r.Count -ge 1
}

# --- Reset password ---
Test-Endpoint "PATCH /students/reset-password" {
    $body = '{"email":"matheus@gmail.com","newPassword":"novaSenha123"}'
    Invoke-RestMethod "$baseUrl/students/reset-password" -Method PATCH -Body $body -ContentType "application/json" | Out-Null
    # Verify login with new password
    $r = Invoke-RestMethod "$baseUrl/auths/login" -Method POST -Body '{"email":"matheus@gmail.com","password":"novaSenha123"}' -ContentType "application/json"
    $r.role -eq "STUDENT"
}

# --- Restore password ---
Invoke-RestMethod "$baseUrl/students/reset-password" -Method PATCH -Body '{"email":"matheus@gmail.com","newPassword":"senha123"}' -ContentType "application/json" | Out-Null

# --- Summary ---
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Results: $passed passed, $failed failed" -ForegroundColor $(if($failed -eq 0){"Green"}else{"Red"})
Write-Host "========================================" -ForegroundColor Cyan
