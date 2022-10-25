import Utils
import Data.List
import Data.Ord
import Data.Map

type Point = (Int, Int)
type Levels = Map Point Int

collectPoints :: [[Int]] -> Levels
collectPoints rss = fromList [((x, y), r) | (x, rs) <- zip [0..] rss, (y, r) <- zip [0..] rs]

nullifyEntrance :: Levels -> Levels
nullifyEntrance ps = Data.Map.insert (0,0) 0 ps

flood :: Levels -> Int
flood ps
    | snd (findMax ps) == 0 = 0
    | otherwise             = 1 + flood (rise ps)

sinking :: Point -> Int -> Levels -> Bool
sinking (x,y) r ps = (r /= 0) && (or [findWithDefault 1 p ps == 0 | p <- [(x+1, y), (x-1, y), (x, y+1), (x, y-1)]])

rise :: Levels -> Levels
rise ps = foldrWithKey (\(x,y) r ps' -> if sinking (x,y) r ps then adjust (+(-1)) (x,y) ps' else ps') ps ps

solveA :: IO ()
solveA = solve "input/15.txt" (nullifyEntrance . collectPoints . readDigitLines) flood

expandCave :: [[Int]] -> [[Int]]
expandCave pss = [[((p + n + m - 1) `mod` 9) + 1 | n <- [0..4], p <- ps] | m <- [0..4], ps <- pss]

solveB :: IO ()
solveB = solve "input/15.txt" (nullifyEntrance . collectPoints . expandCave . readDigitLines) flood

main :: IO ()
main = solveB