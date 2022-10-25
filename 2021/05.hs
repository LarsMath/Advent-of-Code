import Utils
import Data.Char
import Data.List

type Line = ((Int, Int), (Int, Int))
type Point = (Int, Int)
data OrthogonalLine = Hor Int Int Int | Ver Int Int Int | DiagUp Int Int Int | DiagDown Int Int Int
                        deriving(Eq)

parseCoordinates :: String -> Line
parseCoordinates xs = ((ys !! 0, ys !! 1), (ys !! 2, ys !! 3))
                        where ys = seperatedInts xs                             

isOrthogonal :: Line -> Bool
isOrthogonal ((x1, y1), (x2, y2)) = x1 == x2 || y1 == y2

isOrthOrDiag :: Line -> Bool
isOrthOrDiag ((x1, y1), (x2, y2)) = x1 == x2 || y1 == y2 || x1 - x2 == y1 - y2 || x2 - x1 == y1 - y2

orthogonalize :: Line -> OrthogonalLine
orthogonalize ((x1, y1), (x2, y2))
    | x1 == x2              = Ver x1 (min y1 y2) (max y1 y2)
    | y1 == y2              = Hor (min x1 x2) (max x1 x2) y1
    | x1 - y1 == x2 - y2    = DiagUp (min x1 x2) (max x1 x2) (x1 - y1)
    | x1 + y1 == x2 + y2    = DiagDown (min x1 x2) (max x1 x2) (x1 + y1)

linesCross :: OrthogonalLine -> OrthogonalLine -> [Point]
linesCross (Ver x y1 y2) (Hor w1 w2 z)                = if w1 <= x && x <= w2 && y1 <= z && z <= y2 then [(x, z)] else []
linesCross (Ver x y1 y2) (DiagUp w1 w2 c)             = if w1 <= x && x <= w2 && y1 <= x - c && x - c <= y2 then [(x, x - c)] else []
linesCross (Ver x y1 y2) (DiagDown w1 w2 c)           = if w1 <= x && x <= w2 && y1 <= c - x && c - x <= y2 then [(x, c - x)] else []
linesCross (Hor x1 x2 y) (DiagUp w1 w2 c)             = if x1 <= y + c && y + c <= x2 && w1 <= y + c && y + c <= w2 then [(y + c, y)] else []
linesCross (Hor x1 x2 y) (DiagDown w1 w2 c)           = if x1 <= c - y && c - y <= x2 && w1 <= c - y && c - y <= w2 then [(c - y, y)] else []
linesCross (DiagUp x1 x2 c1) (DiagDown w1 w2 c2)      = if ((c2 + c1) `mod` 2) == 0 && x1 <= x && x <= x2 && w1 <= x && x <= w2  then [(x, x - c1)] else []
                                                        where x = (c2 + c1) `div` 2
linesCross (Ver x y1 y2) (Ver w z1 z2)                = if y1 <= z1 && x == w then [(x,y) | y <- [z1..(min y2 z2)]] else []
linesCross (Hor x1 x2 y) (Hor w1 w2 z)                = if x1 <= w1 && y == z then [(x,y) | x <- [w1..(min x2 w2)]] else []
linesCross (DiagUp x1 x2 c1) (DiagUp w1 w2 c2)        = if x1 <= w1 && c1 == c2 then  [(x, x - c1) | x <- [w1..(min x2 w2)]] else []
linesCross (DiagDown x1 x2 c1) (DiagDown w1 w2 c2)    = if x1 <= w1 && c1 == c2 then  [(x, c1 - x) | x <- [w1..(min x2 w2)]] else []
linesCross _ _ = []

solveA :: IO ()
solveA = solve "input/05.txt" (map parseCoordinates . lines) (length . nub . concat . (\ls -> [linesCross a b | a <- ls, b <- ls, a /= b]) . map orthogonalize . filter isOrthogonal)

solveB :: IO ()
solveB = solve "input/05.txt" (map parseCoordinates . lines) (length . nub . concat . (\ls -> [linesCross a b | a <- ls, b <- ls, a /= b]) . map orthogonalize . filter isOrthOrDiag)

