# five-card-evaluator

A Clojure library designed to parse five card poker hands

## Usage

```
user=> (require 'five-card-evaluator.core)
nil

user=> (hand-strength "As Ks Qs Js 10s")
"Straight Flush A High"

user=> (hand-strength "2s 3s 4d 5s 7s")
"7 High"

```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
