import json

with open("data.json") as data:
    data = dict(json.loads(data.read()))


def find_ways(way, end, end_ways):
    try:
        for element in data[way[-1]]:
            if element in way:
                continue
            if element == end:
                end_ways += [way + [element]]
                break
            cur_way = find_ways(way + [element], end, end_ways)
            if cur_way is None:
                continue
        return end_ways
    except:
        return None


def find_min_way(ways):
    if len(ways) == 0:
        return None
    min_length = len(ways[0])
    min_way = ways[0]
    for way in ways:
        if min_length > len(way):
            min_length = len(way)
            min_way = way
    return min_way


all_ways = {}


for end_key in data:
    if end_key == data["start"] or end_key == "start":
        continue
    end_ways = []
    all_ways[data["start"] + " -> " + end_key] = find_min_way(find_ways([data["start"]], end_key, end_ways))

with open("ways.json", "w") as output:
    output.write(json.dumps(all_ways))





