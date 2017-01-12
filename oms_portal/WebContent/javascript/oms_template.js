// this javascript handle UI for template editing
oms.admin.tasktemplate=[
[100,"Create Project"],
[101,"Draft RFP Management"],
[102,"Teaming","Steve Hopkins"],
[103,"Final RFP Management"],
[104,"Proposla Preparation"],
[105,"1st Draft Prep"],
[106,"Pink Team Review"],
[108,"2nd Draft Prep"],
[109,"Red Team Review(RTR)"],
[110,"Final Prep"],
[111,"Submission"],
[112,"Post Submittal Management"],
[114,"Contract Award"]
];

oms.admin.createTLPanel=function(tasklist){
var panel=Ext.create('Ext.panel.Panel',{
layout:'accordion',
id:'tasktemplateID',
title:'Task Templates',
tbar:[
{text:'New Task Template'}
]

});
var data=oms.admin.tasktemplate;
for(var i=0;i<data.length;i++)
{
var child=oms.admin.createTTItemPanel(data[i]);
panel.add(child);

}
panel.doLayout();
return panel;
};

oms.admin.createTTItemPanel=function(task){
function buildDocTypeHtml()
{
var res="<table width=100%>";
res+='<tr><td width=15% ></td><td>RFP Draft</td><td width=100><img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" ></td></tr>';
res+='<tr><td width=15% ></td><td>NDA</td><td><img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" ></td></tr>';
res+='<tr><td width=15% ></td><td>RFP Final</td><td><img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" ></td></tr>';

res+="<tr><td colspan=3><center><br><a href=''>Add New Doctype</a></center></td></tr>"
res+="</table>";
return res;
}
function buildSearchHtml()
{
return "to do";
}
var docpanel=Ext.create('Ext.panel.Panel',{ 
title:'Required Documents:',
margin:'5 5 5 5',
frame:true,
width:'45%',
minHeight:200, 
scrollable:true,
html:buildDocTypeHtml()
});
var searchPanel=Ext.create('Ext.panel.Panel',{
title:'Search Config:',
margin:'5 5 5 5',
width:'45%',
minHeight:200,
scrollable:true,
frame:true,
html:buildSearchHtml()
});
var panel=Ext.create('Ext.panel.Panel',{
title:"["+task[1]+"]", 
width: '80%',
layout: 'hbox',
tools:[{type:'up'}],
minHeight:320,
frame:true,
items:[
docpanel,searchPanel
]
});
return panel;
};