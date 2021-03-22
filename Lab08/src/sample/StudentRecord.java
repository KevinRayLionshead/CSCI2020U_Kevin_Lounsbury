package sample;

public class StudentRecord {
    private String SSID;
    private float midterm;
    private float assignments;
    private float finalExam;
    private float finalMark;
    private char letterGrade;

    public StudentRecord(String SSID, float midterm, float assignments,
                         float finalExam){

        this.SSID = SSID;
        this.midterm = midterm;
        this.assignments = assignments;
        this.finalExam = finalExam;
        this.finalMark = 100.f;
        this.letterGrade = 'A';
    }

    public String getSSID(){
        return this.SSID;
    }
    public float getMidterm(){
        return this.midterm;
    }
    public float getAssignments(){
        return this.assignments;
    }
    public float getFinalExam(){
        return this.finalExam;
    }
    public float getFinalMark(){
        this.finalMark = this.assignments * 0.2f
                + this.midterm * 0.3f + this.finalExam * 0.5f;
        return this.finalMark;
    }
    public char getLetterGrade(){
        if (getFinalMark() < 50.f){
            this.letterGrade = 'F';
        }
        else if (getFinalMark() < 60.f){
            this.letterGrade = 'D';
        }
        else if (getFinalMark() < 70.f){
            this.letterGrade = 'C';
        }
        else if (getFinalMark() < 80.f){
            this.letterGrade = 'B';
        }
        else{
            this.letterGrade = 'A';
        }
        return this.letterGrade;
    }
}
