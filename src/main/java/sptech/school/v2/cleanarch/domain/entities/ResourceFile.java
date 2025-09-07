package sptech.school.v2.cleanarch.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.InputStream;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ResourceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fileName;
    @NotBlank
    private String fileType;
    @NotBlank
    private String fileLocation;
    @NotBlank
    private Long fileSize;

    @Transient
    private InputStream inputStream;

    public ResourceFile() {
    }

    public ResourceFile(String fileName, String fileType, String fileLocation, Long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileLocation = fileLocation;
        this.fileSize = fileSize;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
