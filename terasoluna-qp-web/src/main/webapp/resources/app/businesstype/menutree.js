// Create a menu from an object written in JSON format

function menuTree(tree) {
	this.id = (tree.id) ? tree.id : '' ;
	this.code = (tree.code) ? tree.code : '' ;
	this.name = tree.name;
	this.parentid = (tree.parentid) ? tree.parentid : '';
	this.level = (tree.level) ? tree.level : '' ;
	this.path = (tree.path) ? tree.path : '' ;
	
	// If it's a cursive menu, create a list of submenu and add it
	if (tree.list) {
		var list = new Array();
		for (var i = 0; i < tree.list.length; i++) {
			list[i] = new menuTree(tree.list[i]);
			list[i].parent = this;
		}
		this.list = new Array();
		this.addList(list);
	}
	
	// Create DOM node for menu
	this.render();
}

// Add a list of items to menu
menuTree.prototype.addList = function(list) {
	if (this.isMenu() && list.length > 0) {
		var n = this.list.length;
		for (var i = 0; i < list.length; i++, n++) {
			this.list[n] = list[i];
		}
	}
}

// Create DOM node for all menu elements
menuTree.prototype.render = function() {
	this.html = document.createElement('li');
	this.html.className = 'option-parent';
	
    // Create node for header
	this.header = document.createElement('li');
	this.header.setAttribute('href', 'javascript:void(0)');
	this.header.me = this;
	this.header.appendChild(document.createTextNode(this.name));
	this.header.onclick = clickHandler;
	
    // Create node for body
	this.body = document.createElement('ul');
	this.body.className = 'option-children';
	
    // Append header and body
	this.html.appendChild(this.header);
	this.html.appendChild(this.body);
	
	this.divider = document.createElement('li');
	
	this.divider.className = '';
	
	// If it has submenus, create DOM node for each item recusively
    if (!this.isMenu()) {
        return;
    }
    for (var i = 0; i < this.list.length; i++) {
        this.list[i].render();
		this.body.appendChild(this.list[i].html);
		if(this.list[i].list != null ) {			
			this.body.appendChild(this.list[i].divider);
		} else {
			this.list[i].html.className ='option';
		}
    }
}


menuTree.prototype.showBody = function() {
	this.body.style.display = '';
	this.header.className = 'option-parent';
}

menuTree.prototype.isOpened = function() {
	return (this.body.style.display == '');
}

// Show menu at place indentified by containerID
// showAll is defaulted by false
menuTree.prototype.show = function(containerID, showAll) {
	this.prepare(showAll);
	var container = document.getElementById(containerID);
	container.innerHTML = '';
	container.appendChild(this.html);
	
}

// Prepare menu before show it
// showAll is defaulted by false
menuTree.prototype.prepare = function(showAll) {
	if (!this.isMenu()) {
		this.header.className = 'option';
        return;
    }
	showAll = true;
	this.header.className = 'option';
	this.body.style.display = (showAll) ? '' : 'none';
	for (var i = 0; i < this.list.length; i++) {
		this.list[i].prepare();
	}
}

// Handler when click on menu items
function clickHandler() {
	var obj = this.me;
	$(this).closest("div").find("input[name*='Id']").val(obj.id);
	$(this).closest("div").find("input[name*='Name']").val(obj.name);
}

menuTree.prototype.isMenu = function() {
    return (this.list) ? true : false;
}
