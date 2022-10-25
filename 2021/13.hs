import Utils
import Data.List

type Point = (Int, Int)
data Fold = X Int | Y Int

folds :: [Fold]
folds = reverse [X 655, Y 447, X 327, Y 223, X 163, Y 111, X 81, Y 55, X 40, Y 27, Y 13, Y 6]

parse :: String -> [Point]
parse = map (\xs -> (xs !! 0, xs !! 1)) . map seperatedInts . lines

fold :: Fold -> [Point] -> [Point]
fold (X x) ps = union [(p,q) | (p,q) <- ps, p < x] [(2 * x - p, q) | (p,q) <- ps, p > x]
fold (Y y) ps = union [(p,q) | (p,q) <- ps, q < y] [(p, 2 * y - q) | (p,q) <- ps, q > y]

solveA :: IO ()
solveA = solve "input/13.txt" parse (length . fold (folds !! 0))

display :: [Point] -> [[Char]]
display ps = [[if (x,y) `elem` ps then '#' else '.' | x <- [0..39]] | y <- [0..5]]

solveB :: IO ()
solveB = solve "input/13.txt" parse (display . (\xs -> foldr fold xs folds))