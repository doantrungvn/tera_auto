var buildData = function () {
    var source = [];
    var list = [];
    // build hierarchical source.
    for (i = 0; i < menuData.length; i++) {
        var item = menuData[i];
        var id = item["businessTypeId"];
        var code = item["businessTypeCode"];
        var name = item["businessTypeName"];
        var parentid = item["parentBusinessTypeId"];
        var level = item["level"];
        var path = item["path"];

        if (list[parentid]) {
            var item = { id: id, code: code, name: name, parentid: parentid, level: level, path: path, item: item };
            if (!list[parentid].list) {
                list[parentid].list = [];
            }
            list[parentid].list[list[parentid].list.length] = item;
            list[id] = item;
        } else {
            list[id] = { id: id, code: code, name: name, parentid: parentid, level: level, path: path, item: item };
            source[id] = list[id];
        }
    }
    
    var tree = {name: "", list: ''};
    var leaf = [];
	  for(var i = 0; i<source.length; i++){
	      if (source[i]){
	    	  leaf.push(source[i]);
	    }
	  }
	  tree.list = leaf;
    return tree;
}
	 