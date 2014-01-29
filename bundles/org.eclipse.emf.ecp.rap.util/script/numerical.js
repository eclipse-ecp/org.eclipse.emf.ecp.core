var handleEvent = function( event ) {

  var regexp = /^[0-9]*$/;
  var text = event.widget.getText();
  if( text.match( regexp ) === null ) {
    event.widget.setBackground( [ 255, 0, 0 ] );
    event.widget.setToolTipText( "The Number you have entered is invalid!" );
  } else {
    event.widget.setBackground( null );
    event.widget.setToolTipText( null );
  }

};