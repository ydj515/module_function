/**
 * captcha 음성 듣기
 */
function audio() {
    $('#soundOn').attr('disabled','disabled');
    var rand = Math.random();
    var agent = navigator.userAgent.toLowerCase();
    var soundUrl = '/cmm/cmm/captchaAudio.do?rand=' + rand;

    if (isInternetExplorer(agent))  { /* IE 경우 */
        audioPlayIE(soundUrl);
    } else if (isChromium(agent))  { /* Edge, Chrome */
        audioPlayChromium(soundUrl);
    } else if (isSafari()) {
    } else {
        alert("음성듣기가 지원되지 않는 브라우저입니다.");
    }
}

function isInternetExplorer(agent) {
    return (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1);
}

function isChromium(agent) {
    return (agent.indexOf("chrome") != -1);
}

function isSafari(agent) {
    return (agent.indexOf("safari") != -1);
}

function audioPlayIE(soundUrl) {
    audioForIEPlayer(soundUrl);
    timer();
}

function audioForIEPlayer(objUrl) {
    $('#soundOn').html(' <bgsound src="' + objUrl + '?agent=msie' + '">');
}

function audioPlayChromium(soundUrl) {
    if(!!document.createElement('audio').canPlayType) {
        try {
            new Audio(soundUrl).play();
        } catch (e) {
            audioForIEPlayer(soundUrl);
        } finally {
            timer();
        }
    } else {
        window.open(soundUrl,'','width=1,height=1');
    }
}

function timer() {
    setTimeout(removeDisable, 6000); // 음성 듣기 버튼을 누르고 6초 후 다시 음성 듣기 button 활성화
}

function removeDisable() {
    $('#soundOn').removeAttr('disabled');
}