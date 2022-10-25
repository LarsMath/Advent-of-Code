import Utils
import Data.List
import Data.Digits

parse :: String -> [([String],[String])]
parse xs = [(take 10 $ words y, drop 11 $ words y) | y <- lines xs]

uniqueLength :: String -> Bool
uniqueLength xs = l == 2 || l == 3 || l == 4 || l == 7
                    where l = length xs

solveA :: IO ()
solveA = solve "input/08.txt" parse (length . filter (uniqueLength) . concat . map snd)

findBy :: (String -> Bool) -> [String] -> String
findBy _ [] = []
findBy f (x:xs)
    | f x       = x
    | otherwise = findBy f xs

findIndex' :: [String] -> String -> Int
findIndex' [] _ = 1
findIndex' (y:ys) x
    | length x == length y && length (x \\ y) == 0  = 0
    | otherwise                                     = 1 + findIndex' ys x

decode :: ([String],[String]) -> Int
decode (xss, ys) = unDigits 10 $ map (findIndex' ws) ys
                    where ws = [w0,w1,w2,w3,w4,w5,w6,w7,w8,w9]
                                where   w1 = findBy (\xs -> 2 == length xs) xss
                                        w7 = findBy (\xs -> 3 == length xs) xss
                                        w4 = findBy (\xs -> 4 == length xs) xss
                                        w8 = findBy (\xs -> 7 == length xs) xss
                                        w6 = findBy (\xs -> 6 == length xs && 5 == length (xs \\ w1)) xss
                                        w3 = findBy (\xs -> 5 == length xs && 3 == length (xs \\ w1)) xss
                                        w2 = findBy (\xs -> 5 == length xs && 3 == length (xs \\ w4)) xss
                                        w5 = findBy (\xs -> 5 == length xs && 0 == length (xs \\ w6)) xss
                                        w9 = findBy (\xs -> 6 == length xs && 2 == length (xs \\ w4)) xss
                                        w0 = findBy (\xs -> 6 == length xs && 2 == length (xs \\ w5)) xss

solveB :: IO ()
solveB = solve "input/08.txt" parse (sum . map decode)