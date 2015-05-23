
function enlargeElementOpacity(elem, obj, max, display){
    
    var display = display || "block";
    obj.ReduceElementOpacity = false;
    clearTimeout(obj.ReduceElementOpacityTimer);
    if (!obj.EnlargeElementOpacity) return;
    if (elem.length !== undefined){
        for (var i = 0; i < elem.length; i++){
            if(elem[i].style.display === 'none'){
                elem[i].style.display = display;
            };
        };    
        var opacity = +elem[0].style.opacity;
        if (opacity >= max) return;
        opacity = (opacity * 10 + 1)/10;
        for (var i = 0; i < elem.length; i++){
            elem[i].style.opacity = opacity;
        };    
    }else{ 
        if(elem.style.display === 'none'){
            elem.style.display = display;
        };
        var opacity = +elem.style.opacity;
        if (opacity >= max) return;
        opacity = (opacity * 10 + 1)/10;
        elem.style.opacity = opacity;
    };
    obj.EnlargeElementOpacityTimer = setTimeout(function(){
        enlargeElementOpacity(elem, obj, max, display);
    }, 30); 
};


function reduceElementOpacity(elem, obj, useTimeout, min, finishFunc){
    obj.EnlargeElementOpacity = false;
    clearTimeout(obj.EnlargeElementOpacityTimer);
    if (!obj.ReduceElementOpacity) return;
    min = min || 0;
    if (elem.length !== undefined){
        var opacity = +elem[0].style.opacity;
        if (opacity * 10 > min*10+1){
            opacity = (opacity * 10 - 1)/10;
        };
        for (var i = 0; i < elem.length; i++){
            elem[i].style.opacity = opacity;
        };    
    }else{ 
        var opacity = +elem.style.opacity;
        if (opacity * 10 > min*10+1){
            opacity = (opacity * 10 - 1)/10;
        };
        elem.style.opacity = opacity;
    };
    if ((opacity * 10 > min * 10 + 1) && (useTimeout)){
        obj.ReduceElementOpacityTimer = setTimeout(function(){
            reduceElementOpacity(elem, obj, useTimeout, min, finishFunc);
        }, 30); 
    }else{ 
        var time = (useTimeout) ? 0 : 50;
        setTimeout(function(){
            if (elem.length !== undefined){
                for (var i = 0; i < elem.length; i++){
                    elem[i].style.opacity = min;
                    if (min === 0) elem[i].style.display = 'none';
                };
            }else{
                elem.style.opacity = min; 
                if (min === 0){
                    elem.style.display = 'none';
                };
            };
            if (!!finishFunc)
                finishFunc();
        }, time);
    };
};


function handleMouseLeave(handler){
    return function(e){
        e = e || event;
        var toElement = e.relatedTarget || e.toElement;
        while (toElement && toElement !== this) {
            toElement = toElement.parentNode;
        };
        if (toElement === this){
            return;
        };
        return handler.call(this, e);
    };
};
             
             
function handleMouseEnter(handler){
    return function(e){
        e = e || event;
        var toElement = e.relatedTarget || e.srcElement;
        while (toElement && toElement !== this) {
            toElement = toElement.parentNode;
        };
        if (toElement === this){
            return;
        };
        return handler.call(this, e);
    };
};

function Point(posX, posY){
    if ((!!posX) && (posY === undefined)){
        this.posX = posX.posX || 0;
        this.posY = posX.posY || 0;
        return;
    }
    this.posX = posX;
    this.posY = posY;
    this.equals = function(other){
        if ((!other.posX) || (!other.posY))
            return;
        return ((this.posX === other.posX) && (this.posY === other.posY));
    };
};

function moveObject(element, obj, startPoint, finishPoint, num, finishFunc){
    num = num || 10;
    var dir = obj.moveDir || 1;
    obj = obj || new Point(element.style.left, element.style.top);
    var Vector = new Point((finishPoint.posX - startPoint.posX), (finishPoint.posY - startPoint.posY));
    obj.posX = (obj.posX * num + dir * Vector.posX)/num;
    obj.posY = (obj.posY * num + dir * Vector.posY)/num;
    element.style.left = obj.posX;
    element.style.top = obj.posY;
    var length = Math.sqrt(Vector.posX * Vector.posX + Vector.posY * Vector.posY);
    var startLength = Math.sqrt((obj.posX - startPoint.posX) * (obj.posX - startPoint.posX) 
            + (obj.posY - startPoint.posY) * (obj.posY - startPoint.posY));
    var finishLength = Math.sqrt((obj.posX - finishPoint.posX) * (obj.posX - finishPoint.posX) 
            + (obj.posY - finishPoint.posY) * (obj.posY - finishPoint.posY));
    if ((length > startLength) && (length > finishLength)){
        setTimeout(function(){
            moveObject(element, obj, startPoint, finishPoint, num, finishFunc);
        }, 20 ); 
    }else{
        if (dir === 1){
            element.style.left = obj.posX = finishPoint.posX;
            element.style.top = obj.posY = finishPoint.posY;
        }else if(dir === -1){
            element.style.left = obj.posX = startPoint.posX;
            element.style.top = obj.posY = startPoint.posY;
        }
        if (!!obj.moveDir) {
            obj.moveDir = 0;
        };
        if (!!finishFunc){
            finishFunc();
        }
    };    
};


function resize(massToResize, obj, dir, finishFunc, startFunc){
    var stepsNum = 5;
    var finishSize = (!!massToResize[0].finishSize) ? massToResize[0].finishSize : massToResize.finishSize;
    if (dir.posX > 0){
        dir.posX = 1;
    }else if (dir.posX < 0){
        dir.posX = -1;
    };
    if (dir.posY > 0){
        dir.posY = 1;
    }else if (dir.posY < 0){
        dir.posY = -1;
    }; 
    if ((massToResize.startSize.posX === finishSize.posX) && (massToResize.startSize.posY === finishSize.posY)){
        return;
    }
    if (((massToResize.resizeDir > 0) 
            && (((massToResize.startSize.posX !== finishSize.posX) && (obj.width/finishSize.posX > (massToResize.startSize.posX + ((stepsNum - 1)/stepsNum) * (finishSize.posX - massToResize.startSize.posX))/finishSize.posX)) 
            || ((massToResize.startSize.posY !== finishSize.posY) && (obj.height/finishSize.posY > (massToResize.startSize.posY + ((stepsNum - 1)/stepsNum) * (finishSize.posY - massToResize.startSize.posY))/finishSize.posY))))){
        for(var i = 0; i < +massToResize.length; i++){
            var object = massToResize[i];
            finishSize = (!!object.finishSize) ? object.finishSize : massToResize.finishSize;
            var startSize = (!!object.startSize) ? object.startSize : massToResize.startSize;
            var Step = new Point(finishSize.posX - startSize.posX, 
                            finishSize.posY - startSize.posY);
            var startPos = (!!object.startPos) ? object.startPos : new Point(0, 0);
            var iterPosX = startPos.posX + massToResize.resizeDir * (dir.posX - 1) * Step.posX/2;
            var iterPosY = startPos.posY + massToResize.resizeDir * (dir.posY - 1) * Step.posY/2;
            var iterWidth = finishSize.posX;
            var iterHeight = finishSize.posY;
            if (i === 0){
                obj.posX = iterPosX;
                obj.posY = iterPosY;
                obj.width = iterWidth;
                obj.height = iterHeight;
            };
            setCss(object, iterPosX, iterPosY, iterWidth, iterHeight);
            object.posX = null;
            object.posY = null;
            object.width = null;
            object.height = null;
        };    
        if (!!finishFunc) 
            finishFunc();
        massToResize.resizeDir = 0;
        return;
    };
    if (((massToResize.resizeDir < 0) 
            && (((massToResize.startSize.posX !== finishSize.posX) && ((obj.width/finishSize.posX < (massToResize.startSize.posX + (1/stepsNum) * (finishSize.posX - massToResize.startSize.posX))/finishSize.posX))) 
            || ((massToResize.startSize.posY !== finishSize.posY) && (obj.height/finishSize.posY < (massToResize.startSize.posY + (1/stepsNum) * (finishSize.posY - massToResize.startSize.posY))/finishSize.posY))))){
        for(var i = 0; i < +massToResize.length; i++){
            var object = massToResize[i];
            finishSize = (!!object.finishSize) ? object.finishSize : massToResize.finishSize;
            var startSize = (!!object.startSize) ? object.startSize : massToResize.startSize;
            var startPos = (!!object.startPos) ? object.startPos : new Point(0, 0);
            var iterPosX = startPos.posX;
            var iterPosY = startPos.posY;
            var iterWidth = startSize.posX;
            var iterHeight = startSize.posY;
            if (i === 0){
                obj.posX = iterPosX;
                obj.posY = iterPosY;
                obj.width = iterWidth;
                obj.height = iterHeight;
            };
            setCss(object, iterPosX, iterPosY, iterWidth, iterHeight);
            object.posX = null;
            object.posY = null;
            object.width = null;
            object.height = null;   
        };    
        if (!!startFunc) 
            startFunc();
        massToResize.resizeDir = 0;
        return;
    };
    for(var i = 0; i < massToResize.length; i++){
        var object = massToResize[i];
        finishSize = (!!object.finishSize) ? object.finishSize : massToResize.finishSize;
        var startSize = (!!object.startSize) ? object.startSize : massToResize.startSize;
        var Step = new Point(finishSize.posX - startSize.posX, 
                            finishSize.posY - startSize.posY);
        var posX = (!!object.posX) ? object.posX : ((!!object.element.css) ? 
            object.element.css("left").substring(0, object.element.css("left").length - 2) :
            object.element.style.left.substring(0, object.element.style.left.length - 2));
        var posY = (!!object.posY) ? object.posY : ((!!object.element.css) ?
            object.element.css("top").substring(0, object.element.css("top").length - 2) :
            object.element.style.top.substring(0, object.element.style.top.length - 2));
        var width = (!!object.width) ? object.width : ((!!object.element.css) ?
            object.element.css("width").substring(0, object.element.css("width").length - 2) :
            object.element.style.width.substring(0, object.element.style.width.length - 2)); 
        var height = (!!object.height) ? object.height : ((!!object.element.css) ?
            object.element.css("height").substring(0, object.element.css("height").length - 2) :
            object.element.style.height.substring(0, object.element.style.height.length - 2));
        var iterPosX = (posX * stepsNum + massToResize.resizeDir * (dir.posX - 1) * Step.posX/2)/stepsNum;
        var iterPosY = (posY * stepsNum + massToResize.resizeDir * (dir.posY - 1) * Step.posY/2)/stepsNum;
        var iterWidth = (width * stepsNum + massToResize.resizeDir * Step.posX)/stepsNum;
        var iterHeight = (height * stepsNum + massToResize.resizeDir * Step.posY)/stepsNum;
        object.posX = iterPosX;
        object.posY = iterPosY;
        object.width = iterWidth;
        object.height = iterHeight; 
        if (i === 0){
            obj.posX = iterPosX;
            obj.posY = iterPosY;
            obj.width = iterWidth;
            obj.height = iterHeight;
        };
        function setCss(object, fPosX, fPosY, fWidth, fHeight, isStart){
            for (var j = 0; j < object.css.length; j++){
                var style = object.css[j];
                if (style === "left"){
                    if (!!object.element.css){
                        object.element.css("left", fPosX);
                    }else{
                        object.element.style.top = fPosX;
                    };
                };
                if (style === "top"){
                    if (!!object.element.css){
                        object.element.css("top", fPosY);
                    }else{
                        object.element.style.top = fPosY;
                    };
                };
                if ((style === "width") || (style === "max-width")){
                    if (!!object.element.css){
                        object.element.css(style, fWidth );
                    }else{
                        object.element.style[style] = fWidth;
                    };
                };
                if ((style === "height") || (style === "max-height")){
                    if (!!object.element.css){
                        object.element.css(style, fHeight + "px");
                    }else{
                        object.element.style[style] = fHeight ;
                    };
                };
                if ((style === "margin-left") || (style === "margin-top")){
                    if (!!object.marginDelete){
                        finishSize = (!!object.finishSize) ? object.finishSize : massToResize.finishSize;
                        var startSize = (!!object.startSize) ? object.startSize : massToResize.startSize;
                        var margin = (style === "margin-left") ?  
                            (fWidth - fHeight * finishSize.posX/finishSize.posY)/2: 
                            (fHeight - fWidth * finishSize.posY/finishSize.posX)/2;
                        object.element.css(style, margin + "px");
                    }else{
                        var side = (style === "margin-left") ?  fWidth : fHeight;
                        if (!!object.element.css){
                            object.element.css(style, side * object.marginCoef + "px");
                        }else{
                            object.element.style[style] = side * object.marginCoef;
                        }; 
                    };
                };
            };    
        };
        setCss(object, iterPosX, iterPosY, iterWidth, iterHeight);
    };
    setTimeout(function(){
        resize(massToResize, obj, dir, finishFunc, startFunc);
    }, 10); 
};


function preventSelection(element, preventSelection){
  preventSelection = preventSelection || false;

  function addHandler(element, event, handler){
    if (element.attachEvent) 
      element.attachEvent('on' + event, handler);
    else 
      if (element.addEventListener) 
        element.addEventListener(event, handler, false);
  }
  function removeSelection(){
    if (window.getSelection) { window.getSelection().removeAllRanges(); }
    else if (document.selection && document.selection.clear)
      document.selection.clear();
  }
  function killCtrlA(event){
    var event = event || window.event;
    var sender = event.target || event.srcElement;

    if (sender.tagName.match(/INPUT|TEXTAREA/i))
      return;

    var key = event.keyCode || event.which;
    if (event.ctrlKey && key === 'A'.charCodeAt(0))  // 'A'.charCodeAt(0) можно заменить на 65
    {
      removeSelection();

      if (event.preventDefault) 
        event.preventDefault();
      else
        event.returnValue = false;
    }
  }

  // не даем выделять текст мышкой
  addHandler(element, 'mousemove', function(){
    if(preventSelection)
      removeSelection();
  });
  addHandler(element, 'mousedown', function(event){
    var event = event || window.event;
    var sender = event.target || event.srcElement;
    preventSelection = !sender.tagName.match(/INPUT|TEXTAREA/i);
  });

  // борем dblclick
  // если вешать функцию не на событие dblclick, можно избежать
  // временное выделение текста в некоторых браузерах
  addHandler(element, 'mouseup', function(){
    if (preventSelection)
      removeSelection();
    preventSelection = false;
  });

  // борем ctrl+A
  // скорей всего это и не надо, к тому же есть подозрение
  // что в случае все же такой необходимости функцию нужно 
  // вешать один раз и на document, а не на элемент
  addHandler(element, 'keydown', killCtrlA);
  addHandler(element, 'keyup', killCtrlA);
};


var getWindowScroll = (window.pageXOffset !== undefined) ?
  function() {
    return {
      left: pageXOffset,
      top: pageYOffset
    };
  } :
  function() {
    var html = document.documentElement;
    var body = document.body;

    var top = html.scrollTop || body && body.scrollTop || 0;
    top -= html.clientTop;

    var left = html.scrollLeft || body && body.scrollLeft || 0;
    left -= html.clientLeft;

    return { top: top, left: left };
  };
