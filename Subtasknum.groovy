import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.issue.MutableIssue;

def issuePar = issue.getParentObject()
          
Double subTaskSum = 0
issuePar.getSubTaskObjects()?.each { subtask ->
    subTaskSum += 1
}

// customfield_10106 自定义字段
//def tgtFieldT = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10106")

def tgtField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("SubtaskNum")

// can work
//issue.setDescription("subTaskSum is>>" + tgtField + ">>" + tgtFieldT + ">>" + subTaskSum)

def changeHolder = new DefaultIssueChangeHolder()
FieldLayoutItem fieldLayoutItem = ComponentAccessor.getFieldLayoutManager().getFieldLayout(issuePar).getFieldLayoutItem(tgtField);


try { 
	MutableIssue mutableIssue = (MutableIssue) issuePar;
	mutableIssue.setCustomFieldValue(tgtField, subTaskSum);
	tgtField.updateValue(fieldLayoutItem, issuePar, new ModifiedValue(issuePar.getCustomFieldValue(tgtField), subTaskSum), changeHolder)
}  catch(Exception ex) {
    println("Catching the exception");
    //  parentIssueStoryPoints + ">>" + storyPointsTotal
    //  issue.setDescription("exception is>>"  + ex.getMessage())
}
