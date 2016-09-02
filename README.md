# kvf_parser
A parser suite for the Key Value File format used for example by valve in the items_game.txt file in CS GO

The <a href="https://github.com/donmahallem/kvf_parser/blob/master/com/github/donmahallem/cs/kvf/KVFInputStream.java">KVFInputStream</a> class reads the "items_game.txt" from a provided InputStream and returns "dirty" json.
As there are inside multiple keys with the same name in an object inside the kvf file extra measures must be taken to properly parse the file.
One option is to use the TypeAdapters provided in the gson package folder.
