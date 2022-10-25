import Utils
import Data.Char

parse :: String -> [(String, String)]
parse xs = [(takeWhile (/='-') xs, tail . dropWhile (/='-') $ xs), (tail . dropWhile (/='-') $ xs, takeWhile (/='-') xs)]

smallCave :: String -> Bool
smallCave = and . map isLower

buildPaths :: [(String, String)] -> [[String]] -> [[String]]
buildPaths edges paths = (filter (\(p:ps) -> p == "end") paths) ++ [((snd e):(p:ps)) | (p:ps) <- paths, e <- edges, p /= "end", p == fst e, not (smallCave (snd e) && (snd e) `elem` ps)]

buildAllPaths :: [(String, String)] -> [[String]] -> [[String]]
buildAllPaths es xs
    | length newPaths == length xs  = xs
    | otherwise                     = buildAllPaths es (newPaths)
        where newPaths = buildPaths es xs

solveA :: IO ()
solveA = solve "input/12.txt" (concat . map parse . lines) (length . filter (\(x:xs) -> x == "end") . (\xs -> buildAllPaths xs [["start"]]))

buildPaths' :: [(String, String)] -> [([String],Bool)] -> [([String],Bool)]
buildPaths' edges paths = (filter (\((p:_),_)-> p == "end") paths) ++ [(((snd e):(p:ps)), b || smallCave (snd e) && (snd e) `elem` ps) | ((p:ps),b) <- paths, e <- edges, snd e /= "start", p /= "end", p == fst e, not (b && smallCave (snd e) && (snd e) `elem` ps)]

buildAllPaths' :: [(String, String)] -> [([String],Bool)] -> [([String],Bool)]
buildAllPaths' es xs
    | length newPaths == length xs  = xs
    | otherwise                     = buildAllPaths' es (newPaths)
        where newPaths = buildPaths' es xs

solveB :: IO ()
solveB = solve "input/12.txt" (concat . map parse . lines) (length . filter (\((x:_),_) -> x == "end") . (\xs -> buildAllPaths' xs [(["start"],False)]))