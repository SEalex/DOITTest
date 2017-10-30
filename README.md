    Description
    
API works as an image gallery.
As a user, using a client application, I can:
- sign up / sign in / log out;
- scroll input fields;
- hide keyboard by tapping on blank place;
- receive understandable errors notifications;
- view added images;
- add a new image;
- generate GIF.

    Screens
    
-Login;
-Pictures list:
    -image section includes the image, address and weather (these data are returned from API);
-Upload new picture:
    -image;
    -description;
    -#tag;
    -the coordinates of your current location or coordinates of image Metadata (if any).
-GIF images generation:
    -the method returns a link to the GIF image consisting of the last 5 uploaded images;
    -the app shows a popup with the GIF image over the pictures list.
