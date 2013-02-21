About
=====

`Anconpar` is an address parser for Android. It reads address data from plain
text or geo uri intents and applies a basic logic to parse it into contact
fields. It then prepares the creation of a new contact with the found data.

Motivation
==========

Android's calendar format allows the specification of a `location` at which
the appointment is supposed to take place. Since addresses are usually stored in
contacts, it would seem intuitive to access existing contact data from the
calendar's new event activity.

But not only is this access unsupported (in the calendar app that comes with
gapps as well as in other major calendars from the play store), the export of an
entered location as a new contact is unsupported as well.

To circumvent the later problem, `anconpar` registers for intents containing geo
data and supports the user in creating new contacts from it by parsing the data
and prefilling the fields of a `New Contact` activity.

As an additional use case, `anconpar` supports parsing of plain text selections
shared by popular browsers.

Usage
=====

`Anconpar` hooks into the `Complete action using` menu for intents displaying
geo location data on maps and into the `Choose an action for text` for intents
sharing plain text. Select the `To Contact` entry and edit the parsed data in
the `New Contact` activity before saving the new contact.

Logic
=====

`Anconpar` applies very basic logic for parsing the unstructured data it
receives:
 * Prepare
   * If the data contains newline characters, use them to split it
   * Else use the default delimiter
 * Categorize each component:
   * If it matches against the regexs for street or city, append it to the
     `address` field
     * Else if there is no `name` yet, set it as the name
     * Else append it to the `notes` field

Status
======

This app is under development and lacks basic features:
 * Configuration of regexs for recognizing address components
 * Configuration of delimiter symbol
 * Configuration of precedence of newline delimiter

Acknowledgements
================

The icon is a colorized version of the one provided by
[Iconshock's](http://www.iconshock.com/) [free Android Icon
Set](http://www.iconshock.com/android-icons/). Thx a lot for sharing!
