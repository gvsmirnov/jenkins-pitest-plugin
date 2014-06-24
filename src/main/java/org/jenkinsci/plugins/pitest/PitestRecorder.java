package org.jenkinsci.plugins.pitest;

import hudson.model.*;
import hudson.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * Stores the pitest reports so that they are available later on
 */
public class PitestRecorder extends Recorder {

    private final String reportsGlob;

    @DataBoundConstructor
    public PitestRecorder(String reportsGlob) {
        this.reportsGlob = reportsGlob;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        if (build.getResult().isBetterOrEqualTo(Result.UNSTABLE)) {
            FilePath buildRoot = new FilePath(build.getRootDir());
            FilePath[] reports = build.getWorkspace().list(reportsGlob);

            listener.getLogger().format("Recording pitest reports from %s to %s", buildRoot, reports);

            for(FilePath report : reports) {
                listener.getLogger().format("Recording %s", report);
                //TODO: copy
            }

        }

        return true;
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public String getDisplayName() {
            return "Record pitest report";
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }
    }
}
