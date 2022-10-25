import Utils
import Data.List

computeA :: [Int] -> Int
computeA xs = sum [abs (x - p) | x <- xs]
                where p = xs !! (length xs `div` 2)

solveA :: IO ()
solveA = solve "input/07.txt" seperatedInts (computeA . sort)

mse :: Int -> [Int] -> Int
mse p xs = sum [(x - p) * (x - p) + abs (x - p)) | x <- xs]

computeB :: [Int] -> Int
computeB xs = bestMSE (minimum xs) xs
            where bestMSE n xs 
                    | mse n xs < mse (n+1) xs   = n
                    | otherwise                 = bestMSE (n+1) xs

solveB :: IO ()
solveB = solve "input/07.txt" seperatedInts computeB