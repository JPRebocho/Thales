# Thales

##Notes
API minimum requirement was lowered from 23 to 22 for testing purposes
(change made on the gradle)

## Bugs
*Gallery* error when picking an image. *Photos* and *Explorer* work fine.
Its not about permissions, its how the image is returned from the *Gallery* intent (the sketchy path that the emulator uses on the computer) need to try on a real device.

## To Do List
- [ ] Make the loading screen.

- [x] Add option to use the device's file explorer to open any kind of file.
- [x] Hide Image View frame when there is nothing to show.
- [ ] **Show all pictures of a folder instead of only showing one image**
- [ ] Open data files (depends on the format SOPHIE is sending)

- [x] Change Refresh button for an animated image.
  - added vector asset file to drawable folder (ic_refresh_black_24dp.xml).
  - on activity_main- changed the Button to an ImageButton, changed color to white and removed background.
  - added animation on Main.java.
- [x] little visual bug on the button on the emulator.
- [ ] Need to check when to stop the animation (after the list is refreshed or after a timeout).
- [ ] Make the Image view keep the last image there so when you change intent and come back it will appear.
- [ ] Add option to show full size Image.
- [ ] Add option to close the Image.
- [ ] Add option to see the next/previous image by just draging it to the side.
- [x] Bluetooth Icon added replacing the B_ON ImageButton.
- [x] Merge B_ON with B_OFF.
- [ ] Make Bluetooth Icon fade when its off and turn brighter when its on.
- [x] Make the app search for bluetooth devices as soon as the Bluetooth is turned on or the main window is shown with the Bluetooth already on.
- [ ] **Need to search for new devices.**
- [x] Long press on Bluetooth Icon to open device default bluetooth screen