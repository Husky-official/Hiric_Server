package controllers.jobs;

import models.hiring.JobPosting;

import java.util.ArrayList;

public class ReturnJobs {
    public ArrayList<JobPosting> jobPosts;
    public ReturnJobs(ArrayList<JobPosting> jobPosts) {
        this.jobPosts = jobPosts;
    }
}
