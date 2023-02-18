import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.issue.MutableIssue;

def issuePar = issue.getParentObject()
          
def changeHolder = new DefaultIssueChangeHolder()

Double subTaskSum = 0
Double tmp = 3600
String sta = ""
try { 
    Double totalTime = 0

    issuePar.getSubTaskObjects()?.each { subtask ->
        subTaskSum += 1
        sta += subtask.getStatus().getName() + ">>" + subtask.getOriginalEstimate() + "| "
        if (subtask.getOriginalEstimate() != null) {
             totalTime += subtask.getOriginalEstimate()
        }
    }
    
    //issue.setDescription("sta>>" + sta +">>totalTime>>"  +  totalTime / tmp )

    //def tgtFieldT = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10106")
    def tgtField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("SubtaskTimeEstimate")
    FieldLayoutItem fieldLayoutItem = ComponentAccessor.getFieldLayoutManager().getFieldLayout(issuePar).getFieldLayoutItem(tgtField);
    MutableIssue mutableIssue = (MutableIssue) issuePar;
	mutableIssue.setCustomFieldValue(tgtField, subTaskSum);
	tgtField.updateValue(fieldLayoutItem, issuePar, new ModifiedValue(issuePar.getCustomFieldValue(tgtField),  totalTime / tmp ), changeHolder)

}  catch(Exception ex) {
    //issue.setDescription("e>>"  + ex.getMessage())
}

