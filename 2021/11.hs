import Utils
import Data.List

type Point = (Int, Int, Int)

thrd :: (a, b, c) -> c
thrd (a, b, c) = c

collectOctopi :: [[Int]] -> [Point]
collectOctopi xss = concat [[(a,b,x) | (x, a) <- zip xs [0..]] | (xs, b) <- zip xss [0..]]

energyUp :: [Point] -> [Point]
energyUp xs = extraEnergy xs $ findLights [] xs
                where findLights ys zs 
                        | length (delta) == 0   = ys
                        | otherwise             = findLights (ys ++ delta) (zs \\ delta)
                            where delta = [(x,y,p) | (x,y,p) <- zs, 1 + p + length (filter (\(w,z,q) -> abs (w-x) <= 1 && abs (y-z) <= 1) ys) > 9]

extraEnergy :: [Point] -> [Point] -> [Point]
extraEnergy xs ys = [(x, y, 1 + p + length (filter (\(w,z,q) -> abs (w-x) <= 1 && abs (y-z) <= 1) ys)) | (x,y,p) <- (xs \\ ys)] ++ [(x,y,0) | (x,y,p) <- ys]

countZero :: [Point] -> Int
countZero = length . filter (== 0) . map thrd

solveA :: IO ()
solveA = solve "input/11.txt" (collectOctopi . readDigitLines) (sum . map countZero . take 101 . iterate energyUp)

solveB :: IO ()
solveB = solve "input/11.txt" (collectOctopi . readDigitLines) (length . takeWhile (\ps -> countZero ps /= 100) . iterate energyUp)