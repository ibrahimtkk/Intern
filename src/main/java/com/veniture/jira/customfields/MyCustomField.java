package com.veniture.jira.customfields;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.TextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Scanned
public class MyCustomField extends TextCFType {
    private static final Logger log = LoggerFactory.getLogger(MyCustomField.class);

    public MyCustomField(@JiraImport  CustomFieldValuePersister customFieldValuePersister, @JiraImport GenericConfigManager genericConfigManager) {
    super(customFieldValuePersister, genericConfigManager);
}
    
    @Override
    public Map<String, Object> getVelocityParameters(final Issue issue,
                                                     final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {
        final Map<String, Object> map = super.getVelocityParameters(issue, field, fieldLayoutItem);

        // This method is also called to get the default value, in
        // which case issue is null so we can't use it to add currencyLocale
        if (issue == null) {
            return map;
        }

         FieldConfig fieldConfig = field.getRelevantConfig(issue);
         //add what you need to the map here

        return map;
    }
}