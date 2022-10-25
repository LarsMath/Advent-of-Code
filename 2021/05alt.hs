import Utils
import Data.List

type Line = (Int, Int, Int, Int, Int, Int, Int)
type Point = (Int, Int)

parseCoordinates :: String -> Line
parseCoordinates xs = (y2 - y1, x1 - x2, x1*y2 - x2*y1, min x1 x2, max x1 x2, min y1 y2, max y1 y2)
                        where [x1, y1, x2, y2] = seperatedInts xs                             

isOrthogonal :: Line -> Bool
isOrthogonal (a, b, _, _, _, _, _) = a == 0 || b == 0

isOrthOrDiag :: Line -> Bool
isOrthOrDiag (a, b, _, _, _, _, _) = a == 0 || b == 0 || a == b || a == -b

onLine :: Point -> Line -> Bool
onLine (x,y) (a, b, c, x1, x2, y1, y2) = a * x + b * y + c == 0 && x1 <= x && x <= x2 && y1 <= y && y <= y2

intersectPoints :: (Line, Line) -> [Point]
intersectPoints ((a, b, c, x1, x2, y1, y2), l)
    | b /= 0    = [(x, -(a * x + c) `div` b) | x <- [x1..x2], -(a * x + c) `mod` b == 0, onLine (x, -(a * x + c) `div` b) l]
    | a /= 0    = [(-(b * y + c) `div` a, y) | y <- [y1..y2], -(b * y + c) `mod` a == 0, onLine (-(b * y + c) `div` a, y) l]
    | otherwise = if onLine (x1, y1) l then [(x1, y1)] else []

intersectPoints :: (Line, Line) -> [Point]
intersectPoints ((a1, b1, c1, x1, x2, y1, y2), (a2, b2, c2, w1, w2, z1, z2))
    | a1 * b2 == a2 * b1
    | b /= 0    = [(x, -(a * x + c) `div` b) | x <- [x1..x2], -(a * x + c) `mod` b == 0, onLine (x, -(a * x + c) `div` b) l]
    | a /= 0    = [(-(b * y + c) `div` a, y) | y <- [y1..y2], -(b * y + c) `mod` a == 0, onLine (-(b * y + c) `div` a, y) l]
    | otherwise = if onLine (x1, y1) l then [(x1, y1)] else []

solveA :: IO ()
solveA = solve "input/05.txt" (map parseCoordinates . lines) (length . {-foldr union [] .-} map intersectPoints . distinctCombinations . filter isOrthogonal)

solveB :: IO ()
solveB = solve "input/05.txt" (map parseCoordinates . lines) (length . foldr union [] . map intersectPoints . distinctCombinations . filter isOrthOrDiag)