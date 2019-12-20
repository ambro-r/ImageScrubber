# Image Scrubber

## Objectives: 

A small application that, given a specific directory, will inspect all images within that directory and...

1) attempt to to identify "arty" images generated by digital camera (this is primarily be based on the image metadata).
2) attempt to identify blurry images.

Once identified, these images will be moved to a sub-directory so that they can be reviewed and deletd. 

## Usage:

From command line, run:

    ImageScrubber <DIRECTORY>

... where `<DIRECTORY>` is the directory that contains the images you want to scrub. 

For example: `ImageScrubber "c:\temp\myimages"`
