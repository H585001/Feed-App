package group4.feedapp.analytics.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("poll")
public class Poll {
	@Id
    private Long id;
	private String question;
	private int noCount;
	private int yesCount;
	private Date startTime;
	private Date endTime;
	private boolean isPublic;
	private int status;
	private String accessCode;
	
	public Poll() {}
	
	public Poll(String question, int noCount, int yesCount, Date startTime, Date endTime,
			boolean isPublic, int status, String accessCode) {
		this.question = question;
		this.noCount = noCount;
		this.yesCount = yesCount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isPublic = isPublic;
		this.status = status;
		this.accessCode = accessCode;
	}

	public Long getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getNoCount() {
		return noCount;
	}

	public void setNoCount(int noCount) {
		this.noCount = noCount;
	}

	public int getYesCount() {
		return yesCount;
	}

	public void setYesCount(int yesCount) {
		this.yesCount = yesCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	@Override
	public String toString() {
		return "Poll [id=" + id + ", question=" + question + ", noCount=" + noCount + ", yesCount=" + yesCount
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isPublic=" + isPublic + ", status=" + status
				+ ", accessCode=" + accessCode + "]";
	}

}
