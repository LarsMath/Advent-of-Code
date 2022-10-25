import Utils
import Data.Digits
import Data.Char

hexToBinary :: String -> [Int]
hexToBinary [] = []
hexToBinary (x:xs)
    | isDigit x = toBin4 (ord x - ord '0') ++ hexToBinary xs
    | otherwise = toBin4 (10 + ord x - ord 'A') ++ hexToBinary xs
        where toBin4 n = tail . digits 2 $ 16 + n

dropLiteral :: [Int] -> [Int]
dropLiteral (x:xs)
    | x == 1    = dropLiteral (drop 4 xs)
    | otherwise = drop 4 xs

parsePacket :: Int -> [Int] -> Int
parsePacket n xs
    | and [x == 0 | x <- xs]            = 0
    | take 3 (drop 3 xs) == [1,0,0]     = unDigits 2 (take 3 xs)
    | xs !! 7 == 0                      = unDigits 2 (take 3 xs) + parsePacket (drop 22 xs)
    | otherwise                         = unDigits 2 (take 3 xs) + parsePacket (drop 18 xs)

solveA :: IO ()
solveA = solve "input/16.txt" hexToBinary parsePacket

solution :: String -> Int
solution = parsePacket . hexToBinary