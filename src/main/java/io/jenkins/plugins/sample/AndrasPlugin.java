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
    public static final class AndrasPluginDescriptor extends ViewsTabBarDescriptor {
        private String globalName;

        private String globalDescription;

        public String getGlobalName() {
            return globalName;
        }

        public void setGlobalName(String globalName) {
            this.globalName = globalName;
        }
        public String getGlobalDescription() {
            return globalDescription;
        }

        public void setGlobalDescription(String globalDescription) {
            this.globalDescription = globalDescription;
        }
        public AndrasPluginDescriptor() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "ANDRA Custom Views TabBar";
        }

        @Override
        public boolean configure (StaplerRequest req, JSONObject formData) throws FormException {
            if (!formData.getString("globalName").matches("[a-zA-Z\\s]+")) {
                formData.replace("globalName", globalName);

            }
            req.bindJSON(this, formData);

            save();
            return false;
        }

        public FormValidation doCheckGlobalName(@QueryParameter String globalName) {
            if (globalName.matches("[a-zA-Z\\s]+")) {
                return FormValidation.ok();
            }
            return FormValidation.warning("Name can only contain lowercase and uppercase letters and spaces");
        }
    }
}
