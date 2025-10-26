    package sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher;

    public class TeacherStatsDTO {
        private int totalProfessores;
        private double mediaHorasMes;
        private double valorHoraMedio;
        private double totalHorasTrabalhadas;

        public TeacherStatsDTO() {
        }

        public TeacherStatsDTO(int totalProfessores, double mediaHorasMes, double valorHoraMedio, double totalHorasTrabalhadas) {
            this.totalProfessores = totalProfessores;
            this.mediaHorasMes = mediaHorasMes;
            this.valorHoraMedio = valorHoraMedio;
            this.totalHorasTrabalhadas = totalHorasTrabalhadas;
        }

        public int getTotalProfessores() {
            return totalProfessores;
        }

        public void setTotalProfessores(int totalProfessores) {
            this.totalProfessores = totalProfessores;
        }

        public double getMediaHorasMes() {
            return mediaHorasMes;
        }

        public void setMediaHorasMes(double mediaHorasMes) {
            this.mediaHorasMes = mediaHorasMes;
        }

        public double getValorHoraMedio() {
            return valorHoraMedio;
        }

        public void setValorHoraMedio(double valorHoraMedio) {
            this.valorHoraMedio = valorHoraMedio;
        }

        public double getTotalHorasTrabalhadas() {
            return totalHorasTrabalhadas;
        }

        public void setTotalHorasTrabalhadas(double totalHorasTrabalhadas) {
            this.totalHorasTrabalhadas = totalHorasTrabalhadas;
        }
    }

