package group4.feedapp.API.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Poll {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String question;
	private int noCount;
	private int yesCount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	
	private boolean isPublic;
	private int status;
	private String accessCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
	@JsonIgnore
	private FAUser creator;
	
	@OneToMany (fetch = FetchType.LAZY, mappedBy = "votePoll", cascade={CascadeType.REMOVE})
	@CascadeOnDelete
	@JsonIgnore
	private List<Vote> userVotes;
	
	@OneToMany (fetch = FetchType.LAZY, mappedBy = "iotVotePoll", cascade={CascadeType.REMOVE})
	@CascadeOnDelete
	@JsonIgnore
	private List<IoTVotes> iotVotes;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="linkedPoll")
	@JsonIgnore
	private List<IoTDevice> linkedDevices;
	
	public Poll() {}
	
	public Poll(String question, int noCount, int yesCount, Date startTime, Date endTime,
			boolean isPublic, int status, String accessCode, FAUser creator) {
		this.question = question;
		this.noCount = noCount;
		this.yesCount = yesCount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isPublic = isPublic;
		this.status = status;
		this.accessCode = accessCode;
		this.creator = creator;
		this.userVotes = new ArrayList<Vote>();;
		this.iotVotes = new ArrayList<IoTVotes>();;
	}

	public Poll(String question, int noCount, int yesCount, Date startTime, Date endTime,
			boolean isPublic, int status, String accessCode, FAUser creator, List<Vote> userVotes,
			List<IoTVotes> iotVotes, List<IoTDevice> devices) {
		this.question = question;
		this.noCount = noCount;
		this.yesCount = yesCount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isPublic = isPublic;
		this.status = status;
		this.accessCode = accessCode;
		this.creator = creator;
		this.userVotes = userVotes;
		this.iotVotes = iotVotes;
		this.linkedDevices = devices;
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

	public FAUser getCreator() {
		return creator;
	}

	public void setCreator(FAUser creator) {
		this.creator = creator;
	}

	public List<Vote> getUserVotes() {
		return userVotes;
	}

	public void setUserVotes(List<Vote> userVotes) {
		this.userVotes = userVotes;
	}

	public List<IoTVotes> getIotVotes() {
		return iotVotes;
	}

	public void setIotVotes(List<IoTVotes> iotVotes) {
		this.iotVotes = iotVotes;
	}
	
	public List<IoTDevice> getLinkedDevices() {
		return linkedDevices;
	}

	public void setLinkedDevices(List<IoTDevice> devices) {
		this.linkedDevices = devices;
	}

	@Override
	public String toString() {
		return "Poll [id=" + id + ", question=" + question + ", noCount=" + noCount + ", yesCount=" + yesCount
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isPublic=" + isPublic + ", status=" + status
				+ ", accessCode=" + accessCode + ", creator=" + creator.getName();
	}

}
