var types = {
    
    cookieUserFrame : "cookieUserFrame",
    dialogsContainer : "dialogsWindow",
    dialog : "dialog",
    dialogWindow : "dialogWindow",
    glyph : "glyph",
    glyphFrame : "glyphFrame",
    logInWindow : "logInWindow",
    menu : "menu",
    menuButton : "menuButton", 
    message : "message",
    notificationWindow : "notificationWindow",
    photoComment : "photoComment",
    photoCommentsWindow : "photoCommentsWindow",
    photoDescWindow : "photoDescWindow",
    photoFrame : "photoFrame",
    photoLikesWindow : "photoLikesWindow",
    scrollObject : "scrollObject",
    searchDropdown : "searchDropdown",
    searchMenu : "searchMenu",
    signUpWindow : "signUpWindow",
    userFrame : "userFrame",
    usersContainer : "usersContainer",
    waitingAnimation : "waitingAnimation",
    warningWindow : "warningWindow"
    
};


function createDomObject(obj){
    var other = document.getElementById(obj.id);
    if (!!other) return;
    switch(obj.type){
            
        case types.cookieUserFrame : 
            obj.position = "fixed";
            obj.side = (mainContainer.screenWidth > mainContainer.screenHeight) ?
                                mainContainer.screenWidth/20 : mainContainer.screenHeight/20;
            obj.side = (obj.side >= 40) ? obj.side : 40;
            obj.width = obj.height = obj.side;
            obj.addActiveObjects = function(){
                var glyph = new letsObject("getAvatarPhoto", obj.side * 0.1, obj.side * 0.1, obj.side * 0.4, obj.side * 0.4, types.glyphFrame, 
                    {
                        opacity: 0.6, 
                        parentId: obj.id,
                        onclick: function(){getPhoto(mainContainer.userAvatar);}
                    });
                mainContainer.objects.add(glyph);    
            };
            obj.deleteActiveObjects = function(){
                var getPhotoGlyph = mainContainer.objects.get("getAvatarPhoto");
                if (!!getPhotoGlyph){
                    getPhotoGlyph.delete();
                }else{
                    getPhotoGlyph = document.getElementById("getAvatarPhoto");
                    if (!!getPhotoGlyph){
                        getPhotoGlyph.parentNode.removeChild(getPhotoGlyph);
                    };
                }
            };
            obj.delete = function(){
                obj.ReduceElementOpacity = true;
                reduceElementOpacity(element, obj, true, 0);
                moveObject(element, obj, new Point(0.0), new Point(0, obj.side), 10, function(){deleteLetsObject(obj);});
            };
            var element = createElement(obj, "div", new Point(0, obj.side));
            element.innerHTML = "<div id='cookieAvatarOuterTrimmer' style='overflow: hidden; width: " + obj.side + "px; height: " + obj.side + "px;'>"
                              + "   <div id='cookieAvatarInnerTrimmer' style='margin-top: 0px; margin-left: 0px;'>"
                              + "       <img id='cookieAvatarImg' src='images/" + mainContainer.userAvatar + "low.jpg' alt='" 
                              + mainContainer.userAvatar + "' style='cursor: pointer;'/>"
                              + "   </div>"
                              + "</div>";
            var elementJQuery = $("#" + obj.id);
            var innerTrimmer = $("#cookieAvatarInnerTrimmer");
            var outerTrimmer = $("#cookieAvatarOuterTrimmer");
            var cookieAvatarImg = $("#cookieAvatarImg");
            var marginCoef;
            var img = new Image();     
            img.src = "images/" + mainContainer.userAvatar + "low.jpg";
            img.onload = function() {      
                var cookieWidth = this.width;
                var cookieHeight = this.height;
                if((cookieWidth < obj.side * 2) || (cookieWidth < obj.side * 2)){
                    document.getElementById("cookieAvatarImg").src = "images/" + mainContainer.userAvatar + ".jpg";
                };
                if (cookieWidth > cookieHeight){
                    cookieWidth = (cookieWidth / cookieHeight) * obj.width;
                    cookieHeight = obj.height;
                    cookieAvatarImg.css("max-height", element.style.height);
                    innerTrimmer.css("margin-left", -(cookieWidth - obj.height)/2) ;
                    marginCoef = -(cookieWidth - obj.height)/(2 * obj.height);
                }else{
                    cookieHeight = (cookieHeight / cookieWidth) * obj.height;
                    cookieWidth = obj.width;
                    cookieAvatarImg.css("max-width", element.style.width);
                    innerTrimmer.css("margin-top", -(cookieHeight - obj.width)/2) ;
                    marginCoef = -(cookieHeight - obj.width)/(2 * obj.width);
                };
                obj.massToResize = {
                    startSize: new Point(obj.width, obj.height),
                    finishSize: new Point(cookieWidth * 2, cookieHeight * 2),
                    resizeDir: 0,
                    length: 4,
                    0:{ element: elementJQuery, 
                        css: ["left", "top", "width", "height"]
                      },
                    1:{ element: outerTrimmer, css: ["width", "height"]},
                    2:{ element: innerTrimmer, 
                        css: ["width", "height", (cookieWidth > cookieHeight) ? "margin-left" : "margin-top"], 
                        marginCoef: marginCoef, 
                        marginDelete: true
                      },
                    3:{ element: cookieAvatarImg, 
                        css: [(cookieWidth > cookieHeight) ? "max-height" : "max-width"],
                        finishSize: new Point(cookieWidth * 2 + 1, cookieHeight * 2 + 1)
                      }
                };
            
                element.onmouseout=handleMouseLeave(function(){
                                    obj.ReduceElementOpacity = true;
                                    reduceElementOpacity(element, obj, true, 0.7);
                                    if (obj.massToResize.resizeDir > 0){
                                        obj.massToResize.resizeDir = -1;
                                    }else if (obj.massToResize.resizeDir === 0){
                                        obj.massToResize.resizeDir = -1;
                                        obj.deleteActiveObjects();
                                        setTimeout(function(){
                                            resize(obj.massToResize, obj, new Point(1, 1), obj.addActiveObjects);
                                        }, 200); 
                                    }; 
                });
                element.onmouseover = handleMouseEnter(function(){
                                    obj.EnlargeElementOpacity = true;
                                    enlargeElementOpacity(element, obj, 1);
                                    if (obj.massToResize.resizeDir < 0){
                                        obj.massToResize.resizeDir = 1;
                                    }else if (obj.massToResize.resizeDir === 0){
                                        obj.massToResize.resizeDir = 1;
                                        resize(obj.massToResize, obj, new Point(1, 1), obj.addActiveObjects);
                                    }; 
                });
            } ;    
            
            element.onclick = function(){
                getUser(element);
            };
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, 0.7);
            moveObject(element, obj, new Point(obj), new Point(0, 0));
            break;
        
        
        case types.dialog :
            obj.position = "absolute";
            var finishPoint = new Point(obj);
            var element = createElement(obj, "div", new Point(0, - obj.height));
            break;
            
            
        case types.dialogsContainer :
            obj.position = "absolute";
            obj.delete = function(){
                var element = document.getElementById(obj.id);
                mainContainer.getMoreContainerElements = null;
                if (!!element){
                    obj.ReduceElementOpacity = true;
                    mainContainer.containerHeight = null;
                    reduceElementOpacity(element, obj, true, 0);
                    moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10), 10, function(){
                        var children = mainContainer.objects.children(obj.id);
                        for (var i = 0; i < children.length; i++){
                            if (!!children[i].delete){
                                children[i].delete();
                            }else{
                                deleteLetsObject(children[i]);
                            };
                        };
                        deleteLetsObject(obj);
                    });
                }else{
                    mainContainer.objects.delete(obj.id);
                };
            };     
            if (!!mainContainer.currentContainer){
                mainContainer.currentContainer.delete();
            };
            mainContainer.currentContainer = obj;
            obj.dialogsCount = 0;
            obj.getDialogs = function(){
                obj.lastId = obj.lastId || "-1";
                Ajax("GET", "getdialogs", null, {lastId: obj.lastId},
                    function(response){
                        var dialogs = JSON.parse(response);
                        if (dialogs.length === 0){
                            obj.delete();
                            return;
                        };
                        console.log(response);
                        if ((obj.dialogsCount > 0) && (dialogs[0].id >= obj.lastId)){
                            console.log("return");
                            return;
                        };
                        
                        var element = document.getElementById(obj.id);
                        var newContainer = false;
                        if (!element){
                            newContainer = true;
                            element = createElement(obj, "div");
                            element.className = "container";
                        };    
                        
                        obj.setSizes = function(){
                            element.style.left = obj.posX = mainContainer.oldWindowWidth/9;
                            element.style.top = obj.posY = mainContainer.screenHeight/8;
                            element.style.width = obj.width = mainContainer.oldWindowWidth - 2 * mainContainer.oldWindowWidth/9;
                            obj.coef = 150;
                            obj.dialogsMargin = obj.width/obj.coef;
                            obj.dialogWidth = obj.width - 2 * obj.dialogsMargin;
                            obj.dialogHeight = mainContainer.screenHeight/10;
                            obj.dialogsCount = (!!obj.dialogsCount) ? obj.dialogsCount + dialogs.length : dialogs.length;
                            if (obj.dialogsCount > 0){
                                var children = mainContainer.objects.children(obj.id);
                                for (var i = 0; i < children.length; i++){
                                    var Pos = obj.getDialogPosition(i);
                                    if (children[i].type === types.dialog){
                                        children[i].setSizes(Pos.posX, Pos.posY, obj.dialogWidth, obj.dialogHeight);
                                    };
                                };
                                element.style.height = obj.height = dialogs.length * obj.dialogHeight 
                                    + (children.length + 1) * obj.dialogsMargin;
                                    console.log(obj.dialogHeight);
                                mainContainer.containerHeight = obj.height + 2 * obj.posY;
                                mainContainer.setObjAfterResize();    
                            }else{
                                element.style.height = obj.height = mainContainer.oldWindowHeight - obj.posY;
                            };
                            for (var i = 0; i < dialogs.length; i++){
                                dialogs[i].parentId = obj.id;
                                var Pos = obj.getDialogPosition(obj.dialogsCount - dialogs.length + i);
                                if (!mainContainer.objects.get(dialogs[i].id + "_Dialog"))
                                    mainContainer.objects.add(new letsObject(dialogs[i].id + "_Dialog", Pos.posX, Pos.posY, obj.dialogWidth, obj.dialogHeight, types.dialog, dialogs[i]));
                            };
                        };
                        obj.getDialogPosition = function(num){
                            return new Point(
                                obj.dialogsMargin,
                                (num + 1) * obj.dialogsMargin + num * obj.dialogsHeight
                            );
                        };
                        obj.setObjAfterWindowResize = function(){
                            var element = document.getElementById(obj.id);
                            if (!element){
                                obj.delete();
                                return;
                            };
                            if (obj.width === mainContainer.oldWindowWidth - 2 * mainContainer.oldWindowWidth/9){
                                return;
                            }
                            obj.setSizes();
                        };
                        
                        obj.setSizes();
                        obj.noMore = (dialogs.noMore === "true");
                        
                        if (!obj.noMore){
                            if ((mainContainer.oldWindowHeight - getWindowScroll().top - window.innerHeight <= 500) && (!obj.noMore)) {
                                
                            }else{
                                
                            };    
                        }else{
                            mainContainer.getMoreContainerElements = null;
                        };
                        
                        obj.EnlargeElementOpacity = true;
                        enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
                        var finishPoint = new Point(obj.posX, obj.posY + 20);
                        moveObject(element, obj, new Point(obj), finishPoint);
                }); 
            };    
             obj.getDialogs();
            break;
        
        
        case types.glyph :
            obj.position = "absolute";
            var element = createElement(obj, "span", new Point(0, 10));
            element.className = "glyphicon " + obj.class;
            obj.delete = function(){
                this.ReduceElementOpacity = true;
                reduceElementOpacity(element, obj, true, 0);
                moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10), 10, function(){deleteLetsObject(obj);});
            };   
            if (!!obj.fontSize){
                element.style["font-size"] = obj.fontSize;
            };
            element.onmouseout=handleMouseLeave(function(){
                                    obj.ReduceElementOpacity = true;
                                    reduceElementOpacity(element, obj, true, (!!obj.opacity) ? obj.opacity : 0.8);
            });
            element.onmouseover = handleMouseEnter(function(){
                                    obj.EnlargeElementOpacity = true;
                                    enlargeElementOpacity(element, obj, 1);
            });
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 0.8);
            moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10));
            break;
            
            
            
        case types.glyphFrame :
            obj.position = "absolute";
            var element = createElement(obj, "div", new Point(0, 10));
            element.className = "photoGlyphFrame";
            obj.delete = function(){
                this.ReduceElementOpacity = true;
                reduceElementOpacity(element, obj, true, 0);
                moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10), 10, function(){deleteLetsObject(obj);});
            };     
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
            moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10));
            break;
        
        
        
        case types.menu :
            obj.position = "absolute";
            var element = createElement(obj, "div", new Point(- mainContainer.oldWindowWidth/50, 0));
            var width = mainContainer.screenWidth/10;    
            element.style.padding = width/20;
            element.className = "menu";
            obj.moveDir = 0;      
            element.onmouseout=handleMouseLeave(function(){
                                    obj.ReduceElementOpacity = true;
                                    reduceElementOpacity(element, obj, true, 0.4);
                                    width = mainContainer.screenWidth/10;
                                    obj.startPoint = new Point(mainContainer.oldWindowWidth - width/5, 0);
                                    obj.finishPoint = new Point(mainContainer.oldWindowWidth - width, 0);
                                    if (obj.moveDir === 0){
                                        if ((obj.posX !== obj.finishPoint.posX) || (obj.posY !== obj.finishPoint.posY)){
                                            return;
                                        };
                                        obj.moveDir = -1;
                                        moveObject(element, obj, obj.startPoint, obj.finishPoint);
                                    }else if (obj.moveDir === 1){
                                        obj.moveDir = -1;
                                    };
                                    var children = mainContainer.objects.children(obj.id);
                                    for (var i = 0; i < children.length; i++){
                                        children[i].ReduceElementOpacity = true;
                                        reduceElementOpacity(document.getElementById(children[i].id), children[i], true, 0.1);
                                    };
            });
            element.onmouseover = handleMouseEnter(function(){
                                    obj.EnlargeElementOpacity = true;
                                    enlargeElementOpacity(element, obj, 1);
                                    width = mainContainer.screenWidth/10;
                                    obj.startPoint = new Point(mainContainer.oldWindowWidth - width/5, 0);
                                    obj.finishPoint = new Point(mainContainer.oldWindowWidth - width, 0);
                                    if (obj.moveDir === 0){
                                        if ((obj.posX !== obj.startPoint.posX) || (obj.posY !== obj.startPoint.posY)){
                                            return;
                                        };
                                        obj.moveDir = 1;
                                        moveObject(element, obj, obj.startPoint, obj.finishPoint);
                                    }else if (obj.moveDir === -1){
                                        obj.moveDir = 1;
                                    };
                                    var children = mainContainer.objects.children(obj.id);
                                    for (var i = 0; i < children.length; i++){
                                        children[i].EnlargeElementOpacity = true;
                                        enlargeElementOpacity(document.getElementById(children[i].id), children[i], 0.8);
                                    };
            });
            obj.setObjAfterWindowResize = function(){
                var width = mainContainer.screenWidth/10;          
                obj.posX = mainContainer.oldWindowWidth - width/5;
                obj.height = mainContainer.oldWindowHeight;
                obj.width = width;
                element.style.left = obj.posX;
                element.style.padding = width/20;
                element.style.width = obj.width;
                element.style.height = obj.height;      
            };    
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
            moveObject(element, obj, new Point(obj), new Point(obj.posX - mainContainer.oldWindowWidth/50, obj.posY));
            break;   
            
            
            
        case types.menuButton :
            obj.position = "relative";
            var fontSize = obj.fontSize || mainContainer.screenWidth/40; 
            var value = obj.value || "button";
            obj.height = fontSize * 1.5;
            obj.width = "100%";
            var element = createElement(obj, "div", new Point(0, 10));
            element.className = "menuButton";
            element.innerHTML = value;
            element.style["font-size"] = fontSize;
            obj.moveDir = 0;
            element.onmouseout=handleMouseLeave(function(){
                                    obj.ReduceElementOpacity = true;
                                    reduceElementOpacity(element, obj, true, 0.8);
                                    width = mainContainer.oldWindowWidth/10;
                                    var startPoint = new Point(0, 0);
                                    var finishPoint = new Point(-width/20, 0);
                                    if (obj.moveDir === 0){
                                        if ((obj.posX !== finishPoint.posX) || (obj.posY !== finishPoint.posY)){
                                            return;
                                        };
                                        obj.moveDir = -1;
                                        moveObject(element, obj, startPoint, finishPoint, 5);
                                    }else if (obj.moveDir === 1){
                                        obj.moveDir = -1;
                                    };
            });
            element.onmouseover = handleMouseEnter(function(){
                                    obj.EnlargeElementOpacity = true;
                                    enlargeElementOpacity(element, obj, 1);
                                    width = mainContainer.oldWindowWidth/10;
                                    var startPoint = new Point(0, 0);
                                    var finishPoint = new Point(-width/20, 0);
                                    if (obj.moveDir === 0){
                                        if ((obj.posX !== startPoint.posX) || (obj.posY !== startPoint.posY)){
                                            return;
                                        };
                                        obj.moveDir = 1;
                                        moveObject(element, obj, startPoint, finishPoint, 5);
                                    }else if (obj.moveDir === -1){
                                        obj.moveDir = 1;
                                    };
            });
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
            moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10));
            break;    
            
            
            
        case types.scrollObject :
            obj.position = obj.position || "fixed";
            obj.coef = Math.sqrt(mainContainer.screenWidth * mainContainer.screenHeight)/200;
            obj.setSizes = function(){
                obj.setWindowSizes();
                if (!!obj.parentId){
                    var parent = mainContainer.objects.get(obj.parentId);
                    if (obj.side === "width"){
                        obj.posX = parent.posX;
                        obj.posY = parent.posY + parent.height - obj.coef;
                        obj.width = parent.width;
                        obj.height = obj.coef;
                    }else{
                        obj.posX = parent.posX + parent.width - obj.coef;
                        obj.posY = parent.posY;
                        obj.width = obj.coef;
                        obj.height = parent.height;
                    };
                }else{
                    if (obj.side === "width"){
                        obj.posX = 0;
                        obj.posY = obj.innerHeight - obj.coef;
                        obj.width = obj.innerWidth;
                        obj.height = obj.coef;
                    }else{
                        obj.posX = obj.innerWidth - obj.coef;
                        obj.posY = 0;
                        obj.width = obj.coef;
                        obj.height = obj.innerHeight;
                    };
                };
                if (!!element){
                    element.style.left = obj.posX;
                    element.style.top = obj.posY;
                    element.style.width = obj.width;
                    element.style.height = obj.height;
                };    
            };
            
            obj.setWindowSizes = function(){
                var parent;
                if (!!obj.parentId){
                    parent= $("#" + obj.parentId);
                };       
                obj.innerWidth = (!!obj.parentId) ? parent.scrollWidth : window.innerWidth;
                obj.innerHeight = (!!obj.parentId) ? parent.scrollHeight : window.innerHeight;
                obj.parentWidth = (!!obj.parentId) ? parent.width : mainContainer.oldWindowWidth;
                obj.parentHeight = (!!obj.parentId) ? parent.height : mainContainer.oldWindowHeight;
            };
            
            obj.getScrollPos = function(){
                if (!!obj.parentId){
                    var parent = document.getElementById(obj.parentId);
                    var pos = new Point(parent.sctollLeft, parent.sctollTop);
                    return pos;
                };
                return new Point(getWindowScroll().left, getWindowScroll().top);    
            };
            
            obj.setSizes();
            var startPos = new Point(obj);
            var startDeviation = (obj.side === "width")? new Point(0, -obj.height) : new Point(-obj.width, 0);
            var element = createElement(obj, "div", startDeviation);
            element.style.left = obj.posX;
            element.style.top = obj.posY;
            element.style.width = obj.width;
            element.style.height = obj.height;
            element.style.opacity = 1;
            element.className = "scrollObject";
            element.innerHTML = "<div id = '" + obj.id + "Slider' class = 'scrollObjectSlider'></div>";
            obj.slider = $("#" + obj.id + "Slider");
            
            obj.setSliderSizes = function(){
                obj.setWindowSizes();
                if (obj.side === "width"){
                    obj.slider.css("top", 0);
                    obj.slider.css("left", obj.getScrollPos().posX * obj.innerWidth/obj.parentWidth);
                    obj.slider.css("width", obj.innerWidth * obj.innerWidth/obj.parentWidth);
                    obj.slider.css("height", obj.height);
                }else{
                    obj.slider.css("top", obj.getScrollPos().posY * obj.innerHeight/obj.parentHeight);
                    obj.slider.css("left", 0);
                    obj.slider.css("width", obj.width);
                    obj.slider.css("height", obj.innerHeight * obj.innerHeight/obj.parentHeight);
                };
            };
            
            obj.setSliderSizes();
            var finishSize = (obj.side === "width") ? 
                new Point(obj.width, obj.height * 1.5) :
                new Point(obj.width * 1.5, obj.height); 
            var elementJQuery = $("#" + obj.id);
            
            obj.massToResize = {
                    startSize: new Point(obj.width, obj.height),
                    finishSize: finishSize,
                    resizeDir: 0,
                    length: 2,
                    0:{ element: elementJQuery, 
                        css: ["left", "top", "width", "height"],
                        startPos: startPos
                      },
                    1:{
                        element: obj.slider,
                        css: ["width", "height"],
                        startSize: (obj.side === "width")? 
                            new Point(obj.innerWidth * obj.innerWidth/obj.parentWidth, obj.height)
                            : new Point(obj.width, obj.innerHeight * obj.innerHeight/obj.parentHeight),
                        finishSize: (obj.side === "width")? 
                            new Point(obj.innerWidth * obj.innerWidth/obj.parentWidth, obj.height * 1.5)
                            : new Point(obj.width * 1.5, obj.innerHeight * obj.innerHeight/obj.parentHeight)
                    }
            };
            obj.setResizeStartPos = function(){
                if (!obj.massToResize) return;
                if (obj.side === "width"){
                    obj.massToResize[0].startPos.posX = obj.posX;
                }else{
                    obj.massToResize[0].startPos.posY = obj.posY;
                };
            };
            
            element.onmouseout=handleMouseLeave(function(){
                                    if (obj.massToResize.resizeDir > 0){
                                        obj.massToResize.resizeDir = -1;
                                    }else if (obj.massToResize.resizeDir === 0){
                                        obj.massToResize.resizeDir = -1;
                                        setTimeout(function(){
                                            resize(obj.massToResize, obj, new Point(-1, 0));
                                        }, 200); 
                                    }; 
            });
            
            element.onmouseover = handleMouseEnter(function(){
                                    if (obj.massToResize.resizeDir < 0){
                                        obj.massToResize.resizeDir = 1;
                                    }else if (obj.massToResize.resizeDir === 0){
                                        obj.massToResize.resizeDir = 1;
                                        resize(obj.massToResize, obj, new Point(-1, 0));
                                    }; 
            });
            
            obj.setObjAfterWindowResize = function(){
                obj.setSizes();
                obj.setWindowSizes();
                obj.massToResize[0].startPos = new Point(obj);
                var oldFinSize = obj.massToResize.finishSize;
                var oldStartSize = obj.massToResize.startSize;
                obj.massToResize.startSize = new Point(obj.width, obj.height);
                obj.massToResize.finishSize = (obj.side === "width") ? 
                        new Point(obj.width, obj.height * 1.5) :
                        new Point(obj.width * 1.5, obj.height);  
                var sizeCoef = new Point(obj.massToResize.finishSize.posX/oldFinSize.posX, obj.massToResize.finishSize.posY/oldFinSize.posY);
                var object = obj.massToResize[0]; 
                obj.setResizeStartPos();
                var object = obj.massToResize[1]; 
                if (!!object.startSize){
                    object.startSize = (obj.side === "width")? 
                            new Point(obj.innerWidth * obj.innerWidth/obj.parentWidth, obj.height)
                            : new Point(obj.width, obj.innerHeight * obj.innerHeight/obj.parentHeight);
                };
                if (!!object.finishSize){
                    object.finishSize = (obj.side === "width")? 
                            new Point(obj.innerWidth * obj.innerWidth/obj.parentWidth, obj.height * 1.5)
                            : new Point(obj.width * 1.5, obj.innerHeight * obj.innerHeight/obj.parentHeight);
                };
                
                obj.setSliderSizes();
            };
            
            element.addEventListener("mousedown", function(){
                window.onmouseup =  function(){
                    window.onmousemove = function(){};
                    window.onmouseup = function(){};    
                };
                
                window.onmousemove = function(){
                    obj.setWindowSizes();
                    var clickX = event.clientX;
                    var clickY = event.clientY;
                    var object = obj.massToResize[0]; 
                    var other;
                    if (obj.side === "width"){
                        var halfSliderWidth = obj.innerWidth * obj.innerWidth/(2 * obj.parentWidth);
                        var left;
                        if (clickX <= halfSliderWidth){
                            left = 0;
                        }else if (clickX > (obj.width - halfSliderWidth)){
                            left = obj.width - 2 * halfSliderWidth;
                        }else{
                            left = clickX - halfSliderWidth;
                        }
                        window.scrollTo(left * obj.parentWidth/obj.innerWidth, obj.getScrollPos().posY);
                        obj.slider.css("left", left);
                    }else{
                        var halfSliderHeight = obj.innerHeight * obj.innerHeight/(2 * obj.parentHeight);
                        var top;
                        if (clickY <= halfSliderHeight){
                            top = 0;
                        }else if (clickY > (obj.height - halfSliderHeight)){
                            top = obj.height - 2 * halfSliderHeight;
                        }else{
                            top = clickY - halfSliderHeight;
                        }
                        window.scrollTo(obj.getScrollPos().posX, top * obj.parentHeight/obj.innerHeight);
                        mainContainer.setScrollObjects();
                    };
                };
            }, false);
            
            obj.delete = function(){
                this.ReduceElementOpacity = true;
                reduceElementOpacity(element, obj, true, 0);
                var finishPoint = (obj.side === "width")? new Point(obj.posX, obj.posY + obj.height) : new Point(obj.posX + obj.width, obj.posY);
                moveObject(element, obj, new Point(obj), finishPoint, 10, function(){deleteLetsObject(obj);});
            };
            
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
            var finishPoint = (obj.side === "width")? new Point(obj.posX, obj.posY - obj.height) : new Point(obj.posX - obj.width, obj.posY);
            moveObject(element, obj, new Point(obj), finishPoint);
            break;
        
        
        
        case types.userFrame :
            obj.position = "absolute";
            var finishPoint = new Point(obj);
            var element = createElement(obj, "div", new Point(0, - obj.height));
            element.innerHTML = "<div id='" + obj.id + "OuterTrimmer' style='overflow: hidden; width: " + obj.width + "px; height: " + obj.height + "px;'>"
                              + "   <div id='" + obj.id + "InnerTrimmer' style='margin-top: 0px; margin-left: 0px; width: " + obj.width + "px; height: " + obj.height + "px;'>"
                              + "       <img id='" + obj.id + "Img' src='images/" + obj.avatar + "low.jpg' alt='" + obj.avatar + "' style='cursor: pointer;'/>"
                              + "   </div>"
                              + "</div>";
            obj.fontSize = obj.height/10;
            obj.delete = function(){
                var children = mainContainer.objects.children(obj.id);
                for (var i = 0; i < children.length; i++){
                    deleteLetsObject(children[i]);
                };
                deleteLetsObject(obj);
            };
            element.style["font-size"] = obj.height/10;          
            var elementJQuery = $("#" + obj.id);
            var innerTrimmer = $("#" + obj.id + "InnerTrimmer");
            var outerTrimmer = $("#" + obj.id + "OuterTrimmer");
            var userImg = $("#" + obj.id + "Img");
            element.className = "userFrame";
            var loginDiv = createElement({
                id: obj.id + "NameDiv",
                parentId: obj.id
            }, "div");
            loginDiv.style.opacity = 0.8;
            loginDiv.className = "userNameDiv";
            loginDiv.innerHTML = obj.login;
            if ((mainContainer.objects.get(obj.parentId).currentTime - obj.last_online_time) <= 10000){
               var onlineStatusDiv = createElement({
                    id: obj.id + "OnlineStatusDiv",
                    parentId: obj.id
                }, "div");
                onlineStatusDiv.className = "userOnlineStatusDiv";
                onlineStatusDiv.style.opacity = 0.8;
                onlineStatusDiv.innerHTML = "&#9675;";
            };
            var marginCoef;
            var img = new Image();     
            img.src = "images/" + obj.avatar + "low.jpg";
            img.onload = function() {      
                var userWidth = this.width;
                var userHeight = this.height;
                if((userWidth < obj.width) || (userHeight < obj.height)){
                    document.getElementById(obj.id + "Img").src = "images/" + obj.avatar + ".jpg";
                };
                if (userWidth > userHeight){
                    userWidth = (userWidth / userHeight) * obj.width;
                    userHeight = obj.height;
                    userImg.css("max-height", element.style.height);
                    innerTrimmer.css("margin-left", -(userWidth - obj.height)/2) ;
                    marginCoef = -(userWidth - obj.height)/(2 * obj.height);
                }else{
                    userHeight = (userHeight / userWidth) * obj.height;
                    userWidth = obj.width;
                    userImg.css("max-width", element.style.width);
                    innerTrimmer.css("margin-top", -(userHeight - obj.width)/2) ;
                    marginCoef = -(userHeight - obj.width)/(2 * obj.width);
                };
                obj.massToResize = {
                    startSize: new Point(obj.width, obj.height),
                    finishSize: new Point(userWidth, userHeight),
                    resizeDir: 0,
                    length: 3,
                    0:{ element: elementJQuery, 
                        css: ["left", "top", "width", "height"],
                        startPos: finishPoint
                      },
                    1:{ element: outerTrimmer, css: ["width", "height"]},
                    2:{ element: innerTrimmer, 
                        css: ["width", "height", (userWidth > userHeight) ? "margin-left" : "margin-top"], 
                        marginCoef: marginCoef, 
                        marginDelete: true
                      }
                };
                element.onmouseout=handleMouseLeave(function(){
                                obj.deleteActiveObjects();
                                obj.ReduceElementOpacity = true;
                                reduceElementOpacity(loginDiv, obj, true, 0.8);
                                if (!obj.massToResize.startSize.equals(obj.massToResize.finishSize)){
                                    element.style["z-index"] = 115;
                                    if (obj.massToResize.resizeDir > 0){
                                        obj.massToResize.resizeDir = -1;
                                    }else if (obj.massToResize.resizeDir === 0){
                                        obj.massToResize.resizeDir = -1;
                                        setTimeout(function(){
                                            resize(obj.massToResize, obj, new Point(0, 0), obj.addActiveObjects, function(){
                                                element.style["z-index"] = 110;
                                            });
                                        }, 200); 
                                    }; 
                                }else{
                                    element.style["z-index"] = 110;
                                };    
                });
                element.onmouseover = handleMouseEnter(function(){
                                obj.EnlargeElementOpacity = true;
                                enlargeElementOpacity(loginDiv, obj, 1);
                                if (!obj.massToResize.startSize.equals(obj.massToResize.finishSize)){
                                    element.style["z-index"] = 120;
                                    if (obj.massToResize.resizeDir < 0){
                                        obj.massToResize.resizeDir = 1;
                                    }else if (obj.massToResize.resizeDir === 0){
                                        obj.massToResize.resizeDir = 1;
                                        resize(obj.massToResize, obj, new Point(0, 0), obj.addActiveObjects, function(){
                                                element.style["z-index"] = 110;
                                            });
                                    }; 
                                }else{
                                    obj.addActiveObjects();
                                    element.style["z-index"] = 120;
                                };                                     
                });
                
            } ;    
            obj.setSizes = function(posX, posY, side){
                element.style.left = obj.posX = posX;
                element.style.top = obj.posY = posY;
                element.style.width = obj.width = side;
                element.style.height = obj.height = side;
                var userWidth = img.width;
                var userHeight = img.height;
                var imgElement = document.getElementById(obj.id + "Img");
                if (((userWidth < obj.width) || (userHeight < obj.height)) 
                        && (imgElement.src !== "images/" + obj.avatar + ".jpg")){
                    document.getElementById(obj.id + "Img").src = "images/" + obj.avatar + ".jpg";
                }else if (((userWidth >= obj.width) || (userHeight >= obj.height)) 
                        && (imgElement.src !== "images/" + obj.avatar + "low.jpg")){
                    document.getElementById(obj.id + "Img").src = "images/" + obj.avatar + "low.jpg";
                };
                if (userWidth > userHeight){
                    userWidth = (userWidth / userHeight) * obj.width;
                    userHeight = obj.height;
                    userImg.css("max-height", element.style.height);
                    innerTrimmer.css("margin-left", -(userWidth - obj.height)/2) ;
                    obj.massToResize[2].marginCoef = -(userWidth - obj.height)/(2 * obj.height);
                }else{
                    userHeight = (userHeight / userWidth) * obj.height;
                    userWidth = obj.width;
                    userImg.css("max-width", element.style.width);
                    innerTrimmer.css("margin-top", -(userHeight - obj.width)/2) ;
                    obj.massToResize[2].marginCoef = -(userHeight - obj.width)/(2 * obj.width);
                };
                outerTrimmer.css("width", obj.width);
                outerTrimmer.css("height", obj.height);
                innerTrimmer.css("width", obj.width);
                innerTrimmer.css("height", obj.height);
                
                obj.massToResize.startSize = new Point(obj.width, obj.height);
                obj.massToResize.finishSize = new Point(userWidth, userHeight);
                obj.massToResize[0].startPos = new Point(obj);
                obj.fontSize = obj.height/10;
                element.style["font-size"] = obj.height/10;
            };
            obj.addActiveObjects = function(){
                var glyph = new letsObject(obj.id + "DialogGlyph", obj.massToResize.startSize.posX * 0.05, obj.massToResize.startSize.posX * 0.05, null, null, types.glyph, 
                    {
                        opacity: 0.6, 
                        parentId: obj.id,
                        class: "glyphicon-envelope",
                        fontSize: obj.massToResize.startSize.posX * 0.1,
                        onclick: function(){getPhoto(mainContainer.userAvatar);}
                    });
                mainContainer.objects.add(glyph);    
            };
            obj.deleteActiveObjects = function(){
                var children = mainContainer.objects.children(obj.id);
                for (var j = 0; j < children.length; j++){
                    var glyph = children[j];
                    if (glyph.type === types.glyph){
                        glyph.delete();
                    };
                };
            };
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
            moveObject(element, obj, new Point(obj), finishPoint);
            break;
        
        
        
        case types.usersContainer :
            obj.position = "absolute";
            obj.delete = function(){
                var element = document.getElementById(obj.id);
                mainContainer.getMoreContainerElements = null;
                if (!!element){
                    obj.ReduceElementOpacity = true;
                    mainContainer.containerHeight = null;
                    reduceElementOpacity(element, obj, true, 0);
                    moveObject(element, obj, new Point(obj), new Point(obj.posX, obj.posY + 10), 10, function(){
                        var children = mainContainer.objects.children(obj.id);
                        for (var i = 0; i < children.length; i++){
                            if (!!children[i].delete){
                                children[i].delete();
                            }else{
                                deleteLetsObject(children[i]);
                            };
                        };
                        deleteLetsObject(obj);
                    });
                }else{
                    mainContainer.objects.delete(obj.id);
                };
            };     
            if (!!mainContainer.currentContainer){
                mainContainer.currentContainer.delete();
            };
            mainContainer.currentContainer = obj;
            obj.lastLogin = "";
            obj.getUsers = function(search, city, sex, ageFrom, ageTo){
                obj.search = search || "";
                obj.city = city || "";
                obj.sex = sex || "BOTH";
                obj.ageFrom = ageFrom || 0;
                obj.ageTo = ageTo || 0;
                Ajax("GET", "getusers", 
                    {search: obj.search, city: obj.city},
                    {sex: obj.sex, ageFrom: obj.ageFrom, ageTo: obj.ageTo, lastLogin: obj.lastLogin},
                    function(response){
                        var users = JSON.parse(response);
                        if (users.length === 0){
                            obj.delete();
                            return;
                        };
                        if ((obj.usersCount > 0) && (users[0].login <= obj.lastLogin)){
                            console.log("return");
                            return;
                        };
                        obj.lastLogin = users[users.length - 1].login;
                        var element = document.getElementById(obj.id);
                        var newContainer = false;
                        if (!element){
                            newContainer = true;
                            element = createElement(obj, "div");
                            element.className = "container";
                        };    
                        obj.setSizes = function(){
                            element.style.left = obj.posX = mainContainer.oldWindowWidth/9;
                            element.style.top = obj.posY = mainContainer.screenHeight/8;
                            element.style.width = obj.width = mainContainer.oldWindowWidth - 2 * mainContainer.oldWindowWidth/9;
                            obj.usersInString = 6;
                            obj.coef = 150;
                            obj.usersMargin = obj.width/obj.coef;
                            obj.framesSide = (obj.coef - obj.usersInString - 1) * obj.width/(obj.usersInString * obj.coef);
                            var children = $(".userFrame");
                            if (children.length > 0){
                                children = mainContainer.objects.children(obj.id);
                                obj.usersCount = children.length;
                                for (var i = 0; i < children.length; i++){
                                    var Pos = obj.getUserFramePosition(i);
                                    if (children[i].type === types.userFrame){
                                        children[i].setSizes(Pos.posX, Pos.posY, obj.framesSide);
                                    };
                                };
                                element.style.height = obj.height = Math.ceil(children.length/obj.usersInString) * obj.framesSide 
                                    + (Math.ceil(children.length/obj.usersInString) + 1) * obj.usersMargin;
                                mainContainer.containerHeight = obj.height + 2 * obj.posY;
                                mainContainer.setObjAfterResize();    
                            }else{
                                element.style.height = obj.height = mainContainer.oldWindowHeight - obj.posY;
                            };
                            obj.usersCount = (!!obj.usersCount) ? +obj.usersCount + +users.length : +users.length;
                            element.style.height = obj.height = Math.ceil(obj.usersCount/obj.usersInString) * obj.framesSide 
                                + (Math.ceil(obj.usersCount/obj.usersInString) + 1) * obj.usersMargin;
                            mainContainer.containerHeight = obj.height + 2 * obj.posY;
                            mainContainer.setObjAfterResize();    
                            for (var i = 0; i < users.length; i++){
                                users[i].parentId = obj.id;
                                var Pos = obj.getUserFramePosition(obj.usersCount - users.length + i);
                                if (!mainContainer.objects.get(users[i].login + "_userFrame"))
                                    mainContainer.objects.add(new letsObject(users[i].login + "_userFrame", Pos.posX, Pos.posY, obj.framesSide, obj.framesSide, types.userFrame, users[i]));
                            };
                        };
                        obj.getUserFramePosition = function(num){
                            var numX = num % obj.usersInString;
                            var numY = Math.floor(num / obj.usersInString);
                            return new Point(
                                (numX + 1) * obj.usersMargin + numX * obj.framesSide,
                                (numY + 1) * obj.usersMargin + numY * obj.framesSide
                            );
                        };
                        obj.setObjAfterWindowResize = function(){
                            var element = document.getElementById(obj.id);
                            if (!element){
                                obj.delete();
                                return;
                            };
                            if (obj.width === mainContainer.oldWindowWidth - 2 * mainContainer.oldWindowWidth/9){
                                return;
                            }
                            obj.setSizes();
                        };
                        element.style.top = obj.posY = obj.posY - 20;
                        obj.setSizes();
                        obj.noMore = (users.noMore === "true");
                        
                        if (!obj.noMore){
                            if ((mainContainer.oldWindowHeight - getWindowScroll().top - window.innerHeight <= 500) && (!obj.noMore)) {
                                mainContainer.getMoreContainerElements = null;
                                obj.getUsers(search, city, sex, ageFrom, ageTo);
                            }else{
                                mainContainer.getMoreContainerElements = function(){
                                    console.log(mainContainer.oldWindowHeight - getWindowScroll().top - window.innerHeight)
                                    if ((mainContainer.oldWindowHeight - getWindowScroll().top - window.innerHeight <= 500) && (!obj.noMore)) {
                                        console.log("get, last " + obj.lastLogin);
                                        mainContainer.getMoreContainerElements = null;
                                        obj.getUsers(search, city, sex, ageFrom, ageTo);
                                    };    
                                };
                            };    
                        }else{
                            mainContainer.getMoreContainerElements = null;
                        };
                        
                        obj.EnlargeElementOpacity = true;
                        enlargeElementOpacity(element, obj, (!!obj.opacity) ? obj.opacity : 1);
                        var finishPoint = new Point(obj.posX, obj.posY + 20);
                        moveObject(element, obj, new Point(obj), finishPoint);
                });
            };    
            obj.currentTime = new Date();
            obj.getUsers();
            break;
        
        
        case types.waitingAnimation :
            obj.position = "absolute";
            var element = createElement(obj, "div");
            element.className = "waitingAnimation";
            var ballsNum = 20;
            var center = new Point(obj.width/2, obj.height/2);
            var radius = 60;
            var stepAngle = 2 * Math.PI/ballsNum;
            for (var i = 0; i < ballsNum; i++){
                var angle = i * stepAngle;
                var ball = createElement(
                        {
                            id : obj.id + "_ball_" + i,
                            position: "absolute",
                            width: 16,
                            height: 16,
                            posX: center.posX + Math.cos(angle) * radius - 10,
                            posY: center.posY + Math.sin(angle) * radius - 10,
                            parentId: "waitingAnimation"
                        }, "div");
                ball.className = "waitingAnimationBall";     
            };
            function animateWaitingBalls(obj){
                var form = document.getElementById(obj.id);
                if (!form) return;
                var last = 0;
                var balls = [];
                for (var j = 0; j < ballsNum; j++){
                    var ball = document.getElementById(obj.id + "_ball_" + j);
                    balls[j] = ball;
                    if (!ball) return;
                    if (ball.style.opacity === "1"){
                        last = j;
                    };
                };
                var opacity = 1;
                last++;
                last %= ballsNum;
                var stepOpacity = opacity * 2/ballsNum;
                for (var k = 0; k < ballsNum/2 + 1; k++){
                    balls[last].style.opacity = opacity;
                    opacity = opacity - stepOpacity;
                    last--;
                    if (last === -1){
                        last = 19;
                    };
                };
                setTimeout(function(){
                    animateWaitingBalls(obj);
                }, 50); 
            };
            obj.delete = function(){
                obj.ReduceElementOpacity = true;
                reduceElementOpacity(element, obj, true, 0, function(){
                    clearTimeout(obj.timer);
                    deleteLetsObject(obj); 
                });
                
            };
            obj.EnlargeElementOpacity = true;
            enlargeElementOpacity(element, obj, 0.7);
            animateWaitingBalls(obj);
            setTimeout(function(){
                    obj.delete();
            }, 5000);
            break;        
            
    };
};

function createElement(obj, tag, startDeviation){
    var element = document.createElement(tag);
    element.id = obj.id;
    element.style.position = obj.position || "absolute";
    if (obj.height > 0)
        element.style.height = obj.height;
    if (obj.width > 0)
        element.style.width = obj.width;
    if ((!!startDeviation) && (!!startDeviation.posX)){
        obj.posX = obj.posX - startDeviation.posX;
    };
    if ((!!startDeviation) && (!!startDeviation.posY)){
        obj.posY = obj.posY - startDeviation.posY;
    };
    if (!!obj.onclick){
                element.onclick = obj.onclick;
    };      
    if (obj.posX !== null){
        element.style.left = obj.posX;
    };
    if (obj.posY !== null){
    element.style.top = obj.posY;
    };
    element.style.opacity = 0;
    document.getElementById((!!obj.parentId) ? obj.parentId : "mainContainer").appendChild(element);
    return element;
};    