import Utils
import Data.List

flip' :: Char -> Char
flip' ')' = '('
flip' ']' = '['
flip' '}' = '{'
flip' '>' = '<'
flip' x = x

sescore :: String -> String -> Int
sescore xs [] = 0
sescore xs (y:ys)
    | y `elem` "<{[("       = sescore (xs ++ [y]) ys
    | flip' y == last xs    = sescore (init xs) ys
    | otherwise             = score y
        where   score ')' = 3
                score ']' = 57
                score '}' = 1197
                score '>' = 25137

solveA :: IO ()
solveA = solve "input/10.txt" lines (sum . map (sescore ""))

acscore :: String -> String -> Int
acscore [] [] = 0
acscore (x:xs) [] = score x + 5 * acscore xs []
                where   score '(' = 1
                        score '[' = 2
                        score '{' = 3
                        score '<' = 4
acscore xs (y:ys)
    | y `elem` "<{[("       = acscore (xs ++ [y]) ys
    | flip' y == last xs    = acscore (init xs) ys
    | otherwise             = 0

solveB :: IO ()
solveB = solve "input/10.txt" lines ((\xs -> xs !! (length xs `div` 2)) . sort . filter (/=0) . map (acscore ""))