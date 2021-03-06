#Open Imgur Changelog
<b>3.0</b> June 19, 2015
- Revamp of comments
- Up Min SDK to 17 (4.2)
- Change Share Text to Opengur
- Switch to TabLayout from Design Lib and remove Material Tab library
- Default to Dark Theme
- Add xxxhdpi icons
- Start RecyclerView conversions for lists
- Update Subsampling Image View to 3.1.4

<b>2.9.0</b> June 9, 2015
- Better Themeing
- Better token refresh
- Remove Material Dialogs library
- Switch drawer to NavigationView
- Added text suggestion to comment dialog
- Fix dialog styles
- Reduce memory in Full screen
- Turn of translucent navigation bar temporarily 
- Fix Topics crash
- Add Thumbnail quality settings
- Allow direct album link copying 
- Fix UserManager memory leak

<b>2.8.2</b> June 6, 2015
- Better gif support while viewing in full screen
- Added Beta community nav item
- Added product flavors for beta and full release

<b>2.8.1</b> June 3, 2015
- Fix crash while in full screen image viewing

<b>2.8</b> June 3, 2015
- Update account favorites end point for self profiles
- Full photo swiping added. When viewing an album, swiping between photos is enabled in full screen 
- Long pressing an image will now show the download and share options
- Handle user mentions from comments
- Comment Copying
- Fix some theme issues
- Bug fixes
- Add subsampling-scale-image-view for better zoom support
- Update Universal Image Loader to 1.9.4
- Update Fabric
- Update OKHTTP to 2.4.0
- Update SnackBar to 2.5
- Update Material Dialogs to 0.7.5.1
- Update support libraries to 22.2.0
- Update Gif Drawable to 1.1.7
- Remove PhotoView library
- Remove ByakuGallery library

<b>2.7.1</b> May 14, 2015
- Changed app name to Opengur as per Imgur's request
- Copy correct links
- Updated Material Dialogs to 0.7.4.1
- Updated SnackBar to 2.3
- Update gradle to 2.4
- Update android build tools to 1.2.3 

<b>2.7</b> May 3, 2015
- Tags added
- Titles won't display on single image galleries
- Update icons 
- Update SnackBar to 2.0.2
- Update AppCompat to 22.1.1
- Update Material Dialogs to 0.7.3.1
- Update Build Tools to 1.2.2

<b>2.6.2</b> April 24, 2015
- Experimental Settings section added
- Several miscellaneous bug fixes 
- Fix Floating Action Button visibility toggling when scrolling
- Update appcompat to 22.1.0
- Update Material Dialogs to 0.7.2.7
- Update SnackBar to 2.0
- Update Gif Drawable to 1.1.6
- Update Floating Action Button to 1.4.0

<b>2.6.1</b> April 13, 2015
- Fix crashes in GalleryFragment relating to null activity in onCreateOptionsMenu
- Don't animate drawer hamburger menu 

<b>2.6</b> April 12, 2015
- Gallery search added
- Switch Floating Action Button [library](https://github.com/Clans/FloatingActionButton)
- Various crash fixes IllegalStateException regarding fragment transactions 
- Fix for when a user pressed the "deny" button on the login page
- Update Photoview to 1.2.3
- Update Material Dialogs to 0.7.1.2
- Update bottom sheet to a separate fork for custom callbacks

<b>2.5.1</b>April 1, 2015
- Crash fix for P2R [#68](https://github.com/Kennyc1012/OpenImgur/issues/68), thanks [Jawnnypoo](https://github.com/Jawnnypoo)
- Set defeaults to the same as Imgur [#70](https://github.com/Kennyc1012/OpenImgur/issues/70)
- Remove the options from the share Intent 
- Remove default loading image
- Use AppCompat theme for comment dialog, thanks [Jawnnypoo](https://github.com/Jawnnypoo)
- Update fabric to 2.2.2
- Update Gif Drawable to 1.1.3
- Update Material Dialogs to 0.6.7.0
- Update Bottom Sheet to 1.0

<b>2.5</b> March 29, 2015
- Pull to refresh added to grid fragments, thanks [Jawnnypoo](https://github.com/Jawnnypoo)
- Background uploads added
- Link protoctols changed to HTTPS thanks, [zamabe](https://github.com/zamabe)
- Black Theme added
- Notification icon fixed for Lollipop devices, thanks [Jawnnypoo](https://github.com/Jawnnypoo)
- Crash fix for SQL Exception when querying topics [#52](https://github.com/Kennyc1012/OpenImgur/issues/52)
- Login view fixed thanks, [Jawnnypoo](https://github.com/Jawnnypoo)
- Close cursor for subreddit search adapter when fragment is destroyed, thanks [Jawnnypoo](https://github.com/Jawnnypoo)
- Fix wrong text color for share widget
- Cache will be clear every 4 days instead of 7
- Update OKHTTP to 2.3.0 release
- Update Butterknife to 6.1.0 release
- Update Bottomsheet to 0.9 release
- Update Material Dialogs to 0.6.6.1 release

<b>2.4</b> March 19, 2015
- Ability to open direct photo links from imgur 
- Confirmation when exiting the app
- Enable Javascript for Login Webview
- Ability to refresh a gallery item added
- Collapse search view when submitting a search for subreddits, thanks [Jawnnypoo](https://github.com/Jawnnypoo)
- Handle video download links better 
- Various bug fixes
- Update Material Dialogs to version 0.6.4.6

<b>2.3</b> March 14, 2015
- Meme Generator Activitiy added
- Fixed album link handling 
- Various bug fixes relating to the ViewActivity
- Updated SDK to version 22
- Updated Appcompat to version 22
- Update Material Dialogs to version 0.6.4.0

<b>2.2</b> March 7, 2015
- Dark Theme added
- Subreddit searchs will be saved and used as future suggestions
- Handle album links
- Bug fixes
- Updated several libraries 

<b>2.1</b>
- Topics added
- New Icon
- Database calls will use a singleton for read and write databases
- Updated OKHttp to 2.2.0 release
- Bug fixes

<b>2.0</b> February 24, 2015
- New Profile Layout. Users can now view submissions, favorites, and comments of other users as well as messages and uploads for
their own account.
- Random mode added
- Highest Scoring Filter added
- Task Descriptions updated for Lollipop devices
- Better scroll detection
- Various bug fixes 

<b>1.6.3</b> February 1, 2015
- Fix trying to use a recycled bitmap crash when viewing an image full screen.
- Added null and empty check for adding items in an adapter
- Made sure the device can handle the photo/gallery intent before
launching

<b>1.6.2</b> January 28, 2015
- Fix third party login. To login with a a third party, click the logo of desired party, and click allow with the fields
empty, you will then go through the login flow on that website. 

<b>1.6.1</b> January 20, 2015
- Fixed rare chance of not having items from savedInstance in
ViewActivity
- Upload to gallery bug fix
- Settings Fragment fix

<b>1.6</b> January 17, 2015
- All uploads done within the app will now appear in the Uploads tab
which can be accessed via the Navigation Drawer.
- Fix Issues with parceling.
- Fork the byakugallery library and fix the issue of it throwing an
exception if decoding fails. If failure occurs, photos will use the
PhotoViewAttacher for zooming the image.

- Turn off crashlytics logging for caught exceptions, most were just
network issues.
- Allows users to not get prompted when uploading an image when they are
not logged in.
- Allow the DownloadService to accept URLs for downloads.
- Removed stylized dialogs in favor of the system dialogs.

<b>1.5.1</b>January 12, 2015
- Bug fixes with parceling objects
- Previous comments layout updated

<b>1.5</b> January 11, 2015
- Application id change due to being removed from Google Play
- Crashlytics added
- NSFW thumbnails can now be blocked 

<b>1.4</b> January 6, 2015
- Better Image Zooming. Full screen images will now render with Tile Bitmap to support zooming in on sections of the images
to better render large(tall) images
- Comments crash fix
- Fixed bug in cache clearing that would cause the video cache to fail until app is restarted
- Share Intent added
- Grey Theme added

<b>1.3.1</b> Januaru 4, 2015
- Fixed a crash when posting comments
- Organized strings.xml file

<b>1.3</b> January 2, 2015
- Up/Down vote made apparent
- ADB Logging can now be enabled from settings
- Fix comments loading on wrong image
- Ability to select partition for cache (internal/external)
- Code cleanup

<b>1.2</b> December 21, 2014
- Fixed bug in Subreddit searching
- Comments have a new layout that follow material guidelines
- Updated libraries 

<b>1.1</b> December 20, 2014
- Change applicationId as I deleted keystone file -_-
- Color themes added

<b>1.0</b> December 7, 2014
- Initial Release
