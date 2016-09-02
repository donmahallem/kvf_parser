# kvf_parser
A parser suite for the Key Value File format used for example by valve in the items_game.txt file in CS GO

The <a href="https://github.com/donmahallem/kvf_parser/blob/master/com/github/donmahallem/cs/kvf/KVFInputStream.java">KVFInputStream</a> class reads the "items_game.txt" from a provided InputStream and returns "dirty" json.

# ATTENTION!
As there are inside multiple keys with the same name in an object inside the kvf file extra measures must be taken to properly parse the file.
One option is to use the TypeAdapters provided in the gson package folder.

#GSON
A rough Gson TypeAdapter implementation is provided which handles the repeating key names from the KvfInputStream
It features eather pure parsing of the values as String OR you can choose to let it convert String-Values as Integers and/or Floats if it detects those(adds ~6% conversion time).
