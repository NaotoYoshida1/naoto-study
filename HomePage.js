//scroll_effect
$(window).scroll(function () {
    var scrollAnimationElm = document.querySelectorAll('.scroll_up , .scroll_left , .scroll_right');
    var scrollAnimationFunc = function () {
      for (var i = 0; i < scrollAnimationElm.length; i++) {
        var triggerMargin = 150;
        if (window.innerHeight > scrollAnimationElm[i].getBoundingClientRect().top + triggerMargin) {
          scrollAnimationElm[i].classList.add('on');
        }
      }
    }
    window.addEventListener('load', scrollAnimationFunc);
    window.addEventListener('scroll', scrollAnimationFunc);
  });

  $(function(){
    $(".one").each(function(i, elem){
      var one = $(elem).offset().top;
      $(window).on("load scroll resize", function(){
        var two = $(window).height();
        var three = $(window).scrollTop();
        var showClass = "show";
        var timing = 50; // 50px, add to css
        if (three >= one - two + timing){
          $(elem).addClass(showClass);
        } else {
          $(elem).removeClass(showClass);
        }
      });
    });
  });