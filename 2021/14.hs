import Utils
import Data.List
import Data.Map

type Rules = Map (Char, Char) Char

template :: String
template = "NBOKHVHOSVKSSBSVVBCS"

testTemplate :: String
testTemplate = "NNCB"

parse :: String -> ((Char, Char), Char)
parse xs = ((head xs, head (tail xs)), last xs)

applyTemplate :: String -> Rules -> String
applyTemplate xs rs = concat [[a, rs ! (a,b)] | (a,b) <- zip xs (tail xs)] ++ [last xs]

solveA :: IO ()
solveA = solve "input/14.txt" (fromList . Data.List.map parse . lines) ((\ys -> last ys - head ys) . sort . Data.List.map length . group . sort . (\rs -> (iterate (\xs -> applyTemplate xs rs) $ template) !! 10))


generateMap :: Int -> Rules -> Map (Char, Char) (Map Char Integer)
generateMap 0 rs = fromList [((a,b), if a == b then singleton a 2 else fromList [(a, 1), (b, 1)]) | (a,b) <- keys rs]
generateMap n rs = fromList [((a,b), adjust (\n -> n - 1) c $ unionWith (+) (lowerMap ! (a, c)) (lowerMap ! (c, b))) | ((a,b), c) <- assocs rs]
                    where lowerMap = generateMap (n-1) rs


count :: String -> Map (Char, Char) (Map Char Integer) -> (Map Char Integer)
count xs map = Data.List.foldr (adjust (\n -> n - 1)) (unionsWith (+) [map ! (a,b) | (a,b) <- zip xs (tail xs)]) (tail (init xs))


solveB :: IO ()
solveB = solve "input/14.txt" (fromList . Data.List.map parse . lines) ((\m -> maximum (elems m) - minimum (elems m)) . count template . generateMap 10000)

main :: IO ()
main = solveB