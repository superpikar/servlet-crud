/**
 * NaverSmartEditor is a wrapper of naver smart editor. 
 * Using reveal module pattern, see details here https://addyosmani.com/resources/essentialjsdesignpatterns/book/#revealingmodulepatternjavascript
 * @returns { NaverSmartEditor }
 * @author http://twitter.com/superpikar
 */
SweetAlertWrapper = (function(){
	var _privateVar = "@superpikar";
	
	/**
	 * args = {
	 * 	title: 
	 * 	subtitle:
	 * 	confirmButtonText:
	 * 	cancelButtonText:
	 *  messageSuccessTitle:
	 *  messageSuccessSubtitle:
	 *  messageCancelTitle:
	 *  messageCancelSubtitle:
	 *  forwardTo:
	 * }
	 */
	function showSawl(args, type){
		swal({
  		  title: args.title,
  		  text: args.subtitle,
  		  type: type,
  		  showCancelButton: true,
  		  confirmButtonColor: "#DD6B55",
  		  confirmButtonText: args.confirmButtonText,
  		  cancelButtonText: args.cancelButtonText,
  		  closeOnConfirm: false,
  		  closeOnCancel: false
  		},
  		function(isConfirm){
  			if (isConfirm) {
	  		  swal(args.messageSuccessTitle, args.messageSuccessSubtitle, "success");
	  		  window.setTimeout(function(){
	  		  	window.location= args.forwardTo;		  		            
	  		  }, 1000);
  			}
  			else{
  				swal(args.messageCancelTitle, args.messageCancelSubtitle, "error");
  			}
  		});
	}
	
	return {
		showWarning: function(args){
			showSawl(args, 'warning');
		}
	};
})();