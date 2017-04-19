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
		getUserDocumentHtml:function(docs,keys)
		{
			var res='<p><b><u><font size=2 color="#336699">Recent Documents:</font></u></b>';
			res=res+"<ul>";
			for(var i=0;i<docs.length&&i<5;i++){
				doc=docs[i];
				res=res+'<li><font size=1><a href="#" onclick="oms.doc.openDoc('+doc.id+');return false;">'+doc.name+'</a> -- '+doc.type+'</font></li>';
			}
			res=res+"</ul>";
			res=res+'<p><b><u><font size=2 color="#336699">Frequent Search Keywords:</font></u></b>';
			res=res+"<ul>";
			for (var i=0;i<keys.length;i++){
				key=keys[i].replace(/"/g,'').trim();
				res=res+'<li><font size=1><a href="#" onclick="oms.doc.preDocSearch(\''+key+'\');return false;">'+key+'</a></font></li>';
			}
						
			res=res+'</ul>';
			res=res+'  <a href="#" onclick="oms.doc.openDocumentPanel.show();return false;"><img src="css/images/magnify.png"/>Search Document  </a> ';
		return res;
		},
		getUserProjectHtml:function(projs)
		{
			    console.log('...');
			    console.log(projs);
				var res='<a href="" onclick="oms.project.createNewProjPanel.show();return false;"><img src="css/images/shared/icons/fam/add.png"/>Create Project </a> ';
				res=res+'<p><a href="" onclick="oms.project.openProjectPanel.show();return false;"><img src="css/images/shared/icons/fam/grid.png"/>Open Project </a>';
				res=res+'<p><b><u><font size=2 color="#336699">Recent Project:</font></u></b>';
				res=res+"<ul>";
				for(var i=0;i<projs.length;i++){
					 var proj=projs[i];
					res=res+'<li><font size=1><a href="#" onclick="oms.project.openProject('+proj.projId+');return false;">'+proj.projName+'</a> -- '+proj.projOrg+'</font></li>';
				}
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