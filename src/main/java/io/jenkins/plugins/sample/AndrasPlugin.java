package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.model.Item;
import hudson.util.FormValidation;
import hudson.util.Secret;
import hudson.util.ListBoxModel;
import hudson.views.ViewsTabBar;
import hudson.views.ViewsTabBarDescriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AndrasPlugin extends ViewsTabBar {

    @DataBoundConstructor
    public AndrasPlugin() {
        super();
    }
////

    @Extension
    public static final class AndrasPluginDescriptor extends ViewsTabBarDescriptor {

        public static class OptionalBlock {
            private String urlName;

            private String userName;

            private Secret password;

            @DataBoundConstructor
            public OptionalBlock(String urlName, String userName, Secret password) {
                this.urlName = urlName;
                this.userName = userName;
                this.password = password;
            }
        }

        private String globalName;

        private String globalDescription;

        private OptionalBlock optBlock;

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

        public String getUserName() {
            return optBlock.userName;
        }

        public String getUrlName() {
            return optBlock.urlName;
        }

        public Secret getPassword() {
            return optBlock.password;
        }

        public void setOptBlock(OptionalBlock optBlock) {
            this.optBlock = optBlock;
        }

        public AndrasPluginDescriptor() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "ANDRA Custom Views TabBar";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            if (!formData.getString("globalName").matches("[a-zA-Z\\s]+")) {
                formData.replace("globalName", globalName);

            }
            req.bindJSON(this, formData);

            save();
            return false;
        }

        public FormValidation doCheckGlobalName(@QueryParameter String globalName) {
            Jenkins.get().checkPermission(Jenkins.ADMINISTER);
            if (globalName.matches("[a-zA-Z\\s]+")) {
                return FormValidation.ok();
            }
            return FormValidation.warning("Name can only contain lowercase and uppercase letters and spaces");
        }

        public FormValidation doCheckUserName(@QueryParameter String userName) {
            if (userName.matches("[a-z]+")) {
                return FormValidation.ok();
            }
            return FormValidation.error("Username can only contain lowercase letters");
        }

        public ListBoxModel doFillCategoriesItems() {
            Jenkins.get().checkAnyPermission(Jenkins.ADMINISTER, Item.CONFIGURE, Item.CREATE);
            ListBoxModel items = new ListBoxModel();
            items.add("first item", UUID.randomUUID().toString());
            items.add("second item", UUID.randomUUID().toString());

            return items;
        }
    }
}
