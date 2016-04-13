wget -q \
--post-data 'The quick brown fox jumped over the lazy dog.' \
'localhost:9000/?properties={"tokenize.whitespace":"true","annotators":"pos","outputFormat":"json"}' -O -
