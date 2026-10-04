package LogSentinel.dto;

public class AiLogResponse {

    private String analysis;

    public AiLogResponse() {
    }

    public AiLogResponse(String analysis) {
        this.analysis = analysis;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}