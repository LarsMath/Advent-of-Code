import Utils
import Data.Digits
import Data.List

mostCommon :: [Int] -> Int -- Works for 0/1 only
mostCommon xs = (2 * sum xs) `div` (length xs)

computeA :: [[Int]] -> Int
computeA xss = unDigits 2 ys * unDigits 2 [1 - x | x <- ys]
                where ys = map mostCommon . transpose $ xss

solveA :: IO()
solveA = solve "input/03.txt" readDigitLines computeA

treeSearchSingleLeaf :: ([Int] -> Int) -> [[Int]] -> Int
treeSearchSingleLeaf f xss = unDigits 2 $ treeSearchSingleLeafLayer 0 f xss
                                where treeSearchSingleLeafLayer n f xss
                                        | singleWalk n f xss /= []  = singleWalk n f xss
                                        | otherwise                 = singleWalk (n+1) f xss
                                            where   singleWalk n f []   = []
                                                    singleWalk n f [xs] = xs
                                                    singleWalk n f xss
                                                        | n < length (xss !! 0)  = singleWalk (n+1) f $ filter (\xs -> xs !! n == f ys) xss
                                                        | otherwise              = []
                                                            where ys = (transpose xss) !! n

computeB :: [[Int]] -> Int
computeB xss = (treeSearchSingleLeaf mostCommon xss) * (treeSearchSingleLeaf ((\xs -> 1 - mostCommon xs)) xss)

solveB :: IO()
solveB = solve "input/03.txt" readDigitLines computeB