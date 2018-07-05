# Thales

##Notes
API minimum requirement was lowered from 23 to 22 for testing purposes
(change made on the gradle)

## Bugs
*Gallery* error when picking an image. *Photos* and *Explorer* work fine.
Its not about permissions, its how the image is returned from the *Gallery* intent (the sketchy path that the emulator uses on the computer) need to try on a real device.

## To Do List
- [ ] Make the loading screen.

- [ ] **Show all pictures of a folder instead of only showing one image**
- [ ] Open data files (depends on the format SOPHIE is sending)

- [ ] Need to check when to stop the animation (after the list is refreshed or after a timeout).
- [ ] Make the Image view keep the last image there so when you change intent and come back it will appear.
- [ ] Add option to show full size Image.
- [ ] Add option to close the Image.
- [ ] Add option to see the next/previous image by just draging it to the side.
- [ ] Make Bluetooth Icon fade when its off and turn brighter when its on.
- [ ] **Need to search for new devices.**