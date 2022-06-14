package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.util.FormValidation;
import hudson.views.ViewsTabBar;
import hudson.views.ViewsTabBarDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

public class AndrasPlugin extends ViewsTabBar {

    @DataBoundConstructor
    public AndrasPlugin() {
        super();
    }

    @Extension
    public static final class CustomViewsTabBarDescriptor extends ViewsTabBarDescriptor {
        private String globalName;

        private String globalDescription;

        private boolean nameIsOk;

        public String getGlobalName() {
            return globalName;
        }

        public String getGlobalDescription() {
            return globalDescription;
        }

        public CustomViewsTabBarDescriptor() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "ANDRA Custom Views TabBar";
        }

        @Override
        public boolean configure (StaplerRequest req, JSONObject formData) throws FormException {
            if (nameIsOk) {
                globalName = formData.getString("globalName");
            }
            globalDescription = formData.getString("globalDescription");
            save();
            return false;
        }

        public FormValidation doCheckGlobalName(@QueryParameter String globalName) {
            if (globalName.matches("[a-zA-Z\\s]+")) {
                nameIsOk = true;
                return FormValidation.ok();
            }
            else {
                nameIsOk = false;
            }
            return FormValidation.warning("Name can only contain lowercase and uppercase letters and spaces");
        }
    }
}
