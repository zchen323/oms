oms.admin.lookupTaskDetails=function(taskID)
{
	if(oms.admin.cachedata.taskTemplates)
	{
		for(var i=0;i<oms.admin.cachedata.taskTemplates.length;i++)
		{
			var data=oms.admin.cachedata.taskTemplates[i];
			if(data.id==taskID)
				{
					// find a match
					var res=data;
					if(data.config)
					{
						res.docs=Ext.decode(data.config).docs;
					}
					return data;
				}
		}
	}
	return null;

};
oms.admin.refreshData=function()
{
	Ext.Ajax.request({
		url:'api/admin',
		success:function(response)
		{
			var obj=Ext.JSON.decode(response.responseText);
			oms.admin.cachedata=obj.result;
			console.log(oms.admin.cachedata);
			// set user grid
			var p=Ext.getCmp("userlistadmingrid");
			p.getStore().setData(oms.admin.cachedata.users);
			// set doctype
			p=Ext.getCmp("dtadminngrid");
			p.getStore().setData(oms.admin.cachedata.docTypes);
			// project role
			p=Ext.getCmp("projrtadmingrid");
			p.getStore().setData(oms.admin.cachedata.projectUserRoleTypes);
			
			// task template
			oms.admin.buildTLDetails(oms.admin.cachedata.taskTemplates);
			
			// proj templates

			oms.admin.buildPLDetails(oms.admin.cachedata.prjectTemplates);
			
			// populate project template combo box
			Ext.getCmp('ptcombo').getStore().setData(oms.admin.cachedata.prjectTemplates);
		},
		failure: function(response) 
		        { 
		            console.log(response.responseText); 
		        } 

	});
};