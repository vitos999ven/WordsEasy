<html> 
    <head>
        <meta charset="cp1251">
        <title>Lets</title>
        <link rel="stylesheet" type="text/css" href="myStyles.css" media="all" />
        <link rel="stylesheet" type="text/css" href="bootstrap.css" media="all" />
    </head>
    <body>
        <script src="jquery-2.1.1.min.js"></script>
        <script src="AjaxMethod.js"></script>
        <script src="ObjectTypes.js"></script>
        <script src="myMethods.js"></script>
        
        <div id="mainContainer">
            
        </div>
        
        <script>
            setCookie("username", "user1");
            setCookie("useravatar", "197");
            function getCookie(name){
                var cookie = ' ' + document.cookie;
                var search = ' ' + name + '=';
                var setStr = "";
                var offset = 0;
                var end = 0;
                if (cookie.length > 0){
                    offset = cookie.indexOf(search);
                    if (offset !== -1){
                        offset += search.length;
                        end = cookie.indexOf(';',offset);
                        if (end === -1){
                            end = cookie.length;
                        }    
                        setStr = unescape(cookie.substring(offset,end));
                    }
                }
                return (setStr);
            };
            
            function setCookie(name, value, options) {
                options = options || {};
                var expires = options.expires;
                if (typeof expires === "number" && expires) {
                    var d = new Date();
                    d.setTime(d.getTime() + expires*1000);
                    expires = options.expires = d;
                }
                if (expires && expires.toUTCString) {
                    options.expires = expires.toUTCString();
                }
                value = encodeURIComponent(value);
                var updatedCookie = name + "=" + value;
                for(var propName in options) {
                    updatedCookie += "; " + propName;
                    var propValue = options[propName];   
                    if (propValue !== true) {
                        updatedCookie += "=" + propValue;
                    }
                }
                document.cookie = updatedCookie;
            };
            
            function deleteCookie(cookie){
                setCookie(cookie, "", { expires: -1 });
            };
            
            
            
            
            
            
            
            
            function MainContainer(){
                this.authorized = (!!getCookie("username"));
                var container = this;
                this.objects = newObjectsArray();
                this.oldWindowWidth = (window.innerWidth >= 640) ? window.innerWidth : 640;
                this.oldWindowHeight = (window.innerHeight >= 480) ? window.innerHeight: 480;
                this.screenWidth = window.screen.availWidth;
                this.screenHeight = window.screen.availHeight;
                this.element = document.getElementById("mainContainer");
                this.element.style.width = this.oldWindowWidth;
                this.element.style.height = this.oldWindowHeight;
                this.setScrollObjects = function(){
                    if (container.oldWindowWidth > window.innerWidth){
                        var scroll = container.objects.get("widthMainScroll");
                        if (!scroll){
                            container.objects.add(new letsObject("widthMainScroll", null, null, null, null, types.scrollObject, 
                            {
                                side: "width"
                            }));
                        }else{
                            scroll.setSizes();
                            scroll.setSliderSizes();
                            scroll.setResizeStartPos();
                        };    
                    }else{
                        var widthScroll = container.objects.get("widthMainScroll");
                        if (!!widthScroll){
                            if (!!widthScroll.delete)
                                widthScroll.delete();
                            else
                                deleteLetsObject(widthScroll);
                        };    
                    };
                    if (container.oldWindowHeight > window.innerHeight){
                        var scroll = container.objects.get("heightMainScroll");
                        if (!scroll){
                            container.objects.add(new letsObject("heightMainScroll", null, null, null, null, types.scrollObject, 
                            {
                                side: "height"
                            }));
                        }else{
                            scroll.setSizes();
                            scroll.setSliderSizes();
                            scroll.setResizeStartPos();
                        }; 
                    }else{
                        var heightScroll = container.objects.get("heightMainScroll");
                        if (!!heightScroll){
                            if (!!heightScroll.delete)
                                heightScroll.delete();
                            else
                                deleteLetsObject(heightScroll);
                        };    
                    };
                    if (!!container.getMoreContainerElements){
                        container.getMoreContainerElements();        
                    };
                };
                container.setObjAfterResize = function(){
                    
                    container.oldWindowWidth = (window.innerWidth >= 640) ? window.innerWidth : 640;
                    container.oldWindowHeight = ((container.containerHeight >= window.innerHeight)) ? container.containerHeight : ((window.innerHeight >= 480) ? window.innerHeight: 480);
                    container.element.style.width = container.oldWindowWidth;
                    container.element.style.height = container.oldWindowHeight;
                    container.setScrollObjects();
                    for (var i = 0; i < container.objects.length; i++){
                        if (!!container.objects[i].setObjAfterWindowResize){
                            container.objects[i].setObjAfterWindowResize();
                        };
                    };
                };
                window.onresize = container.setObjAfterResize;
                
                this.addDialogsContainer = function(){
                    var usersContainer = container.objects.get("dialogsContainer");
                    if (!!usersContainer){
                        
                    }else{
                        container.objects.add(new letsObject("dialogsContainer", 0, 0, null, null, types.dialogsContainer));
                    };
                };
                
                this.addUsersContainer = function(){
                    var usersContainer = container.objects.get("usersContainer");
                    if (!!usersContainer){
                        
                    }else{
                        container.objects.add(new letsObject("usersContainer", 0, 0, null, null, types.usersContainer));
                    };
                };
                
                if (this.authorized) {
                    Ajax("GET", "checkloginunique", {username : getCookie("username")}, null,
                        function(unique){
                            if (unique === "true"){
                                deleteCookie("username");
                                deleteCookie("useravatar");
                                container.authorized = false;
                                return;
                            };
                            
                            container.userAvatar = getCookie("useravatar");
                            var menuWidth = mainContainer.screenWidth/10;
                            container.objects.add(new letsObject("cookieUserFrame", 0, 0, null, null, types.cookieUserFrame));
                            container.objects.add(new letsObject("menu", container.oldWindowWidth -menuWidth/5, 0, menuWidth, container.oldWindowHeight, types.menu, {opacity: 0.4}));
                            container.objects.add(new letsObject("dialogsMenuButton", 0, 0, 0, 0, types.menuButton, {opacity: 0.1, parentId: "menu", value: "Dialogs",
                                            onclick: function(){mainContainer.addDialogsContainer();}}));
                            container.objects.add(new letsObject("usersMenuButton", 0, 0, 0, 0, types.menuButton, {opacity: 0.1, parentId: "menu", value: "Users", 
                                            onclick: function(){mainContainer.addUsersContainer();}}));
                                        
                            preventSelection(document, false); 
                            //container.objects.add(new letsObject("waitingAnimation", 0, 0, container.oldWindowWidth, window.innerHeight, types.waitingAnimation));
                        });
                };
            };
            
            
            function newObjectsArray(){
                var array = [];
                array.delete = function(id){
                    for (var i=0; i < this.length; i++) {
                        if (this[i].id === id){
                            this.splice(i, 1);
                            break;
                        }
                    };
                };
                
                array.add = function(obj){
                    if ((!obj.id) || (!obj.type)) return;
                    for (var i=0; i < array.length; i++) {
                        if (array[i].id === obj.id){
                            return;
                        }
                    };
                    this.push(obj);
                };
                
                array.get = function(id){
                    for (var i=0; i < this.length; i++) {
                        if (this[i].id === id){
                            return this[i];
                        }
                    };
                };
                
                array.children = function(id){
                    var mass = [];
                    for (var i=0; i < this.length; i++) {
                        if ((!!this[i].parentId) && (this[i].parentId === id)){
                            mass.push(this[i]);
                        };
                    };
                    return mass;
                };
                
                array.first = function(type){
                    for (var i=0; i < this.length; i++) {
                        if (this[i].type === type){
                            return this[i];
                        };
                    }; 
                };
                
                array.last = function(type){
                    var last;
                    for (var i=0; i < this.length; i++) {
                        if (this[i].type === type){
                            last = this[i];
                        };
                    }; 
                    return last;
                };
                
                return array;
            };
            
            
            function letsObject(id, posX, posY, width, height, type, args){
                this.id = id;
                this.posX = (!isNaN(posX)) ? posX : 0;
                this.posY = (!isNaN(posY)) ? posY : 0;
                this.height = (!isNaN(height)) ? height : 0;
                this.width = (!isNaN(width)) ? width : 0;
                this.type = type || "";
                this.ReduceElementOpacity = false;
                this.EnlargeElementOpacity = false;
                if ( {}.toString.call(args) === '[object Object]' ) {
                    for(var key in args) {
                        this[key] = args[key];
                    };
                };
    
                this.setPosX = function(posX){
                    if (isNaN(posX)) return;
                    this.posX = posX;
                };
                this.setPosY = function(posY){
                    if (isNaN(posY)) return;
                    this.posY = posY;
                };
                this.setHeight = function(height){
                    if (isNaN(height)) return;
                    this.height = height;
                };
                this.setWidth = function(width){
                    if (isNaN(width)) return;
                    this.width = width;
                };
                
                this.getMinSideSize = function(){
                    if (this.height <= this.width) return this.height;
                    return this.width;
                };
                this.delete = (!!args && !!args.delete) ? args.delete : function(){deleteLetsObject(this);};
                
                this.clone = function() {
                    var newObj = {};
                    for(var key in this) {
                        newObj[key] = this[key];
                    };
                    return newObj;
                };
                createDomObject(this);
                
            };
            function deleteLetsObject(object){
                var element = document.getElementById(object.id);
                if (!!element)
                    element.parentNode.removeChild(element);
                mainContainer.objects.delete(object.id);
            };
            
            
            
            
            
            function cleanText(text){
                return text.replace(/[\\]/g, '\\\\')
                           .replace(/</g, "&lt;")
                           .replace(/>/g, "&gt;")
                           .replace(/"/g, "&quot;")
                           .replace(/'/g, "&#039;")
                           .replace(/&/g, "!:.");
            };
            
            var mainContainer = new MainContainer();
            mainContainer.setScrollObjects();
            
            
            function addHandler(object, event, handler) {
                if (object.addEventListener) {
                    object.addEventListener(event, handler, false);
                }else if (object.attachEvent) {
                    object.attachEvent('on' + event, handler);
                }else alert("error");
            };
  
            addHandler(window, 'DOMMouseScroll', wheel);
            addHandler(window, 'mousewheel', wheel);
            addHandler(document, 'mousewheel', wheel);
 
 
            function wheel(event) {
                var delta;
                event = event || window.event;
                
                if (event.wheelDelta) { 
                    delta = event.wheelDelta / 120;
                    
                    if (window.opera) delta = -delta; 
                }
                else if (event.detail) { 
                    delta = -event.detail / 3;
                }
                
                if (event.preventDefault) event.preventDefault();
                event.returnValue = false;
                var coef = 10;
                if ((delta > 0) && (getWindowScroll().top < coef)){
                    window.scrollTo(getWindowScroll().left, 0);
                }else if ((delta < 0) && (getWindowScroll().top + window.innerHeight > mainContainer.oldWindowHeight - coef)){
                    window.scrollTo(getWindowScroll().left, mainContainer.oldWindowHeight - window.innerHeight);
                }else{
                    window.scrollBy(0, -delta * coef);
                }
                
                mainContainer.setScrollObjects();
            }
        </script>
    </body>
</html>    