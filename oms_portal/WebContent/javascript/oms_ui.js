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
			res=res+'<li><font size=1><a href="#">Finalize Proposal --  Project: GMST Tracking System</a></font></li>';
			res=res+'<li><font size=1><a href="#">Completed EN Checklist -- Project: SVT Global Delivery </a></font></li>';
			res=res+"</ul>";
			res=res+'<p><b><u><font size=2 color="#336699">Frequent Search Keywords:</font></u></b>';
			res=res+"<ul>";
			res=res+'<li><font size=1><a href="#">Security</a></font></li>';				
			res=res+'<li><font size=1><a href="#">Mobile Development</a></font></li>';	
			res=res+'<li><font size=1><a href="#">Network Engineer</a></font></li>';			
			res=res+'</ul>';
			res=res+'  <a href=""><img src="css/images/magnify.png"/>Search Document  </a> ';
		return res;
		},
		getUserProjectHtml:function(user)
		{
			
				var res='<a href="" onclick="oms.project.createNewProjPanel.show();return false;"><img src="css/images/shared/icons/fam/add.png"/>Create Project </a> ';
				res=res+'<p><a href="" onclick="oms.project.openProjectPanel.show();return false;"><img src="css/images/shared/icons/fam/grid.png"/>Open Project </a>';
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
			res=res+"</ul>";
			return res;
		},
		getAdminHtml:function()
		{
			return '<a href="#" onclick="oms.openAdmin();return false;"><img src="css/images/shared/icons/fam/cog_edit.png"/> Launch Admin Panel </a>';
		},
		getSearchHtml:function()
		{
			var res="<table><tr><td width=100></td><Td>";
			res+="<h3>Related Contents</h3><ul>";
			res+="<li><font color=#336699><a href='#'>Project Documents for Same Agency DOE</a></font></li>";
			res+="<li><font color=#336699><a href='#'>Documents related to <i><u><font color=green>NetWork Security</font></u></i> </a></font></li>";
			res+="<li><font color=#336699><a href='#'>Documents related to <i><u><font color=green>Mobile Development</font></u></i></a></font></li>";
			res+="<li><font color=#336699><a href='#'>Documents related to <i><u><font color=green>Content Management</font></u></i></a></font></li>";
			res+="<li><font color=#336699><a href='#'>Documents related to <i><u><font color=green>Machine Learning</font></u></i></a></font></li>";
			res+="</ul>";
			res+="<a href='#'>Additional Search</a></td></tr></table>";
			return res;
		}
		
};