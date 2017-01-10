oms.ui={
getWorkQueueHtml:function(user)
{
var res='<p><b><u><font size=2 color="#336699">Tasks:</font></u></b>';
res=res+"<ul>";
res=res+'<li><font size=1><a href="#">Upload RFP -- Project: SCCG Database upgrade RFP</a></font></li>';
res=res+'<li><font size=1><a href="#">Finalize Proposal -- Project: GMST Tracking System</a></font></li>';
res=res+'<li><font size=1><a href="#">Complete EN Checklist -- Project: SVT Global Delivery </a></font></li>';
res=res+"</ul>";
res=res+'<p><b><u><font size=2 color="#336699">Review Tasks:</font></u></b>';
res=res+"<ul>";
res=res+'<li><font size=1><a href="#">Review Proporal -- Project: SVT Global Delivery</a></font></li>'; 
res=res+"</ul>";
res=res+'<p><b><u><font size=2 color="#336699">Recent Closed Tasks:</font></u></b>';
res=res+"<ul>"; 
res=res+'<li><font size=1><a href="#">Finalize Proposal -- Project: GMST Tracking System</a></font></li>';
res=res+'<li><font size=1><a href="#">Complete EN Checklist -- Project: SVT Global Delivery </a></font></li>';
res=res+"</ul>";
return res;
},
getUserDocumentHtml:function(user)
{
var res='<p><b><u><font size=2 color="#336699">Recent Documents:</font></u></b>';
res=res+"<ul>";
res=res+'<li><font size=1><a href="#">RFP -- Project: SCCG Database upgrade RFP</a></font></li>';
res=res+'<li><font size=1><a href="#">Finalize Proposal -- Project: GMST Tracking System</a></font></li>';
res=res+'<li><font size=1><a href="#">Completed EN Checklist -- Project: SVT Global Delivery </a></font></li>';
res=res+"</ul>";
res=res+'<p><b><u><font size=2 color="#336699">Frequent Search Keywords:</font></u></b>';
res=res+"<ul>";
res=res+'<li><font size=1><a href="#">Security</a></font></li>'; 
res=res+'<li><font size=1><a href="#">Mobile Development</a></font></li>'; 
res=res+'<li><font size=1><a href="#">Network Engineer</a></font></li>'; 
res=res+'</ul>';
res=res+' <a href=""><img src="css/images/magnify.png"/>Search Document </a> ';
return res;
},
getUserProjectHtml:function(user)
{

var res='<a href=""><img src="css/images/shared/icons/fam/add.png"/>Create Project </a> ';
res=res+'<p><a href=""><img src="css/images/shared/icons/fam/grid.png"/>Open Project </a>';
res=res+'<p><b><u><font size=2 color="#336699">Recent Project:</font></u></b>';
res=res+"<ul>";
res=res+'<li><font size=1><a href="#">SCCG Database upgrade RFP</a></font></li>';
res=res+'<li><font size=1><a href="#">GMST Tracking System</a></font></li>';
res=res+'<li><font size=1><a href="#">Project: SVT Global Delivery </a></font></li>';
res=res+"</ul>";
return res; 
},
getReportHtml:function()
{
var res='';
res=res+'<p><b><u><font size=2 color="#336699">OMS System Reports:</font></u></b>';
res=res+"<ul>";
res=res+'<li><font size=1><a href="#">Project Summary</a></font></li>';
res=res+'<li><font size=1><a href="#">Task Execution Summary</a></font></li>';
res=res+'<li><font size=1><a href="#">System Utilization Summary</a></font></li>';
res=res+'<li><font size=1><a href="#">User Work Queue</a></font></li>';
res=res+"</ul>";
res=res+'<p><b><u><font size=2 color="#336699">My Reports:</font></u></b>';
res=res+"<ul>"; 
res=res+'<li><font size=1><a href="#">01/04/2017 Project Summary</a></font></li>';
res=res+"</ul>"
return res;
},
getAdminHtml:function()
{
 var res='<p><a href=""><img src="css/images/shared/icons/fam/grid.png"/> My Profile</a><p>';
 res=res+'<a href=""><img src="css/images/shared/icons/fam/cog_edit.png"/> Launch Admin Panel </a>';
 return res;
}

};