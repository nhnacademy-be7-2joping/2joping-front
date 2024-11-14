# 주문 페이지 배송비 정책 가져오기

## 요청
```json
{
  "is_member": true
}
```

## 응답
`is_member`에 따라 회원/비회원 관련 정책 가져옴
```json
{
  "policiesList": [
    {
      "shipmentPolicyId": 1,
      "minOrderAmount": 0,
      "shippingFee": 5000
    },
    {
      "shipmentPolicyId": 2,
      "minOrderAmount": 30000,
      "shippingFee": 0
    }
  ]
}
```