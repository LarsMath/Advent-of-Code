import Utils
import Data.List

pad :: [[Int]] -> [[Int]]
pad xss = [(repeat 10)] ++ (map padLine xss) ++ [(repeat 10)]
            where padLine xs = [10] ++ xs ++ [10]

computeA :: [[Int]] -> [Int]
computeA (xs:(ys:(zs:zss))) = (computeLine xs ys zs) ++ (computeA (ys:(zs:zss)))
                                where   computeLine (x0:x1:xs) (y0:y1:y2:ys) (z0:z1:zs)
                                            | y1 < y0 && y1 < y2 && y1 < x1 && y1 < z1  = (y1 + 1) : computeLine (x1:xs) (y1:y2:ys) (z1:zs)
                                            | otherwise                                 = computeLine (x1:xs) (y1:y2:ys) (z1:zs)
                                        computeLine _ _ _ = []
computeA _ = []


solveA :: IO ()
solveA = solve "input/09.txt" readDigitLines (sum . computeA . pad)

type Point = (Int, Int)

collectValleys :: [[Int]] -> [Point]
collectValleys xss = concat [[(a,b) | (x, a) <- zip xs [0..], x /= 9] | (xs, b) <- zip xss [0..]]

collectNeighbours :: [Point] -> [Point] -> [Point]
collectNeighbours _ [] = []
collectNeighbours xs ((x,y):ys) = zs ++ collectNeighbours (xs \\ zs) (ys ++ zs)
                                where zs = intersect xs [(x+1,y), (x, y+1), (x-1,y), (x,y-1)]

refineBasins :: [Point] -> [[Point]]
refineBasins [] = []
refineBasins (p:ps) = b : refineBasins (ps \\ b)
                        where b = p:(collectNeighbours ps [p])

solveB :: IO ()
solveB = solve "input/09.txt" (collectValleys . readDigitLines) (product . take 3 . reverse . sort . map length . refineBasins)
