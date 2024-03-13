function setCookie(cookieName, value, days) {
	// 설정 일수만큼 현재시간에 만료값으로 지정
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + days);
	
	var cookieValue = escape(value) + "; path=/" + ((days == null) ? '' : '; expires=' + exdate.toUTCString());
	document.cookie = cookieName + '=' + cookieValue;
}

function getCookie(cookieName) {
	var x, y;
	var val = document.cookie.split(';');

	for (var i = 0; i < val.length; i++) {
		x = val[i].substr(0, val[i].indexOf('='));
		y = val[i].substr(val[i].indexOf('=') + 1);
		// 앞과 뒤의 공백 제거하기
		x = x.replace(/^\s+|\s+$/g, '');
		
		if (x == cookieName) {
			// unescape로 디코딩 후 값 리턴
			return unescape(y);
		}
	}
}

/**
 * @author pluto#plutozone.com
 * @since 2016-01-07
 *
 * <p>DESCRIPTION: 값 얻기(Get Value)</p>
 * <p>IMPORTANT:</p>
 */
function getOptionValue(objThis) {
	if (!objThis) return "";
	return objThis.options[objThis.selectedIndex].value;
}

/**
 * @author pluto#plutozone.com
 * @since 2016-01-07
 *
 * <p>DESCRIPTION: 값 설정(Set Value)</p>
 * <p>IMPORTANT:</p>
 */
function setOption(objThis, value) {
	if (!objThis) return;
	
	for (var i = 0; i < objThis.options.length; i++) {
		if (objThis.options[i].value == value) {
			objThis.options.selectedIndex = i;
			break;
		}
	}
}

/**
 * @author pluto#plutozone.com
 * @since 2016-01-07
 *
 * <p>DESCRIPTION: 옵션 생성(Create Option)</p>
 * <p>IMPORTANT:</p>
 */
function createOption(objID, objStruct, defaultValue, setValue) {

	var combobox	= document.getElementById(objID);
	
	for (var i = 0; i < combobox.options.length; i++) {
		combobox.options.length = i;
	}
	
	var option		= null;
	if (defaultValue) {
		option = new Option(defaultValue, "");
		combobox.options[0] = option;
	}
		
	if (objStruct && objStruct.length > 0) {
		var j = 0;
		var optionCount = combobox.options.length;
		for (var i=optionCount; i < objStruct.length+optionCount; i++) {
			option = new Option(objStruct[j].text, objStruct[j].value);
			combobox.options[i] = option;
			j++;
		}
	}	
	setOption(combobox, setValue);
}

/**
 * @author pluto#plutozone.com
 * @since 2016-01-07
 *
 * <p>DESCRIPTION: 값 설정(Set Value)</p>
 * <p>IMPORTANT:</p>
 */
function setSelect(objThis, value) {
	if (!objThis) return;
	
	for (var i = 0; i < objThis.options.length; i++) {
		if (objThis.options[i].value == value) {
			objThis.options.selectedIndex = i;
			break;
		}
	}
}

/**
 * @author pluto#plutozone.com
 * @since 2016-01-07
 *
 * <p>DESCRIPTION: 셀렉트 생성(Create Select)</p>
 * <p>IMPORTANT:</p>
 */
function createSelect(objParentId, objID, objStruct, defaultValue, setValue, width) {
	var combobox		= document.createElement("select");
	combobox.id			= objID;
	combobox.name		= objID;
	
	if (width == undefined) width = "100";
	combobox.setAttribute("style", "width: " + width + "px; padding-top: 0px; padding-bottom: 0px;");
	
	var option		= null;
	if (defaultValue) {
		option = new Option(defaultValue, "");
		combobox.options[0] = option;
	}
	
	if (objStruct && objStruct.length > 0) {
		var j = 0;
		var optionCount = combobox.options.length;
		for (var i=optionCount; i < objStruct.length+optionCount; i++) {
			option = new Option(objStruct[j].text, objStruct[j].value);
			combobox.options[i] = option;
			
			j++;
		}
	}
	
	setSelect(combobox, setValue);
	document.getElementById(objParentId).appendChild(combobox);
}

/**
 * @author thepluto#hotmail.com
 * @since 2023-11-02
 *
 * <p>DESCRIPTION: 엘리먼트의 값을 쉼표로 표기</p>
 * <p>IMPORTANT:</p>
 */

function commaValue(object){
	var value		= object.value;
	value			= value.replaceAll(",", "");
	value			= value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	object.value	= value;
}

/* 에러(Error) */
var MSG_ERR_COMMON="에러가 발생하였습니다. 잠시 후에 이용하여 주십시오!";
var MSG_ERR_ID="사용하실 수 없는 아이디입니다!";
var MSG_ERR_ID_PASSWD="아이디와 암호를 확인하세요!";
var MSG_ERR_WRITE="등록에 실패하였습니다. 잠시 후에 이용하여 주십시오!";
var MSG_ERR_MODIFY="수정에 실패하였습니다. 잠시 후에 이용하여 주십시오!";
var MSG_ERR_REMOVE="삭제에 실패하였습니다. 잠시 후에 이용하여 주십시오!";

/* 문의(Ask) */
var MSG_ASK_RMV="삭제 하시겠습니까?";

/* 성공(Success) */
var MSG_SCC_WRITE="등록되었습니다.";
var MSG_SCC_MODIFY="수정되었습니다.";
var MSG_SCC_REMOVE="삭제되었습니다.";