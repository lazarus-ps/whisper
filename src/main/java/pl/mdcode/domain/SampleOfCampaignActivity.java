package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SampleOfCampaignActivity.
 */
@Entity
@Table(name = "sample_of_campaign_activity")
public class SampleOfCampaignActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "activity_name", length = 100, nullable = false)
    private String activityName;

    @Column(name = "list_of_posts")
    private Integer listOfPosts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public SampleOfCampaignActivity activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getListOfPosts() {
        return listOfPosts;
    }

    public SampleOfCampaignActivity listOfPosts(Integer listOfPosts) {
        this.listOfPosts = listOfPosts;
        return this;
    }

    public void setListOfPosts(Integer listOfPosts) {
        this.listOfPosts = listOfPosts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SampleOfCampaignActivity sampleOfCampaignActivity = (SampleOfCampaignActivity) o;
        if (sampleOfCampaignActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleOfCampaignActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleOfCampaignActivity{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            ", listOfPosts='" + getListOfPosts() + "'" +
            "}";
    }
}
