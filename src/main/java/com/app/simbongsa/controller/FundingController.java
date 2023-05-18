package com.app.simbongsa.controller;

import com.app.simbongsa.domain.FileDTO;
import com.app.simbongsa.domain.FundingDTO;
import com.app.simbongsa.domain.FundingItemDTO;
import com.app.simbongsa.domain.MemberDTO;
import com.app.simbongsa.entity.funding.FundingCreator;
import com.app.simbongsa.repository.funding.FundingFileRepository;
import com.app.simbongsa.service.funding.FundingItemService;
import com.app.simbongsa.service.funding.FundingService;
import com.app.simbongsa.service.member.MemberService;
import com.app.simbongsa.type.FileRepresentationalType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/funding/*")
@RequiredArgsConstructor
@Slf4j
public class FundingController {
    private final FundingService fundingService;
    private  final MemberService memberService;

    private final FundingItemService fundingItemService;
    private final FundingFileRepository fundingFileRepository;


    @GetMapping("funding-creator-info")
    public String fundingCreateForm() {return "funding/funding-creater-info";}

    @PostMapping("funding-creator-info")
    public String fundingCreate( FundingDTO fundingDTO) {


        return "funding/funding-creater-info";
    }

    @GetMapping("funding-detail")
    public String fundingDetail() {return "funding/funding-detail";}


//    @GetMapping("funding-detail{fundingId}")
//    public String fundingDetail(@PathVariable Long fundingId, Model model) {
//        model.addAttribute("funding", fundingService.getFundingDetail(fundingId));
//
//        return "/funding/funding-detail";}


    @GetMapping("funding-item")
    public String fundingItemForm() {return "funding/funding-item.html";}


    @PostMapping("funding-item")
    public String fundingItem(FundingItemDTO fundingItemDTO) {

        fundingItemService.ItemSave(fundingItemDTO);
        return "funding/funding-item";}


    @GetMapping("funding-initial-info")
    public String fundingInitialForm() {return "funding/funding-initial-info.html";}


    //업로드는 됨.... db에 저장이 안됌
    @PostMapping("funding-initial-info")
    @Transactional
    public String fundingInitial(@ModelAttribute("fundingDTO") FundingDTO fundingDTO) {

        List<FileDTO> fileDTOS = fundingDTO.getFileDTOs();

        if(fileDTOS != null){
            fileDTOS.get(0).setFileRepresentationalType(FileRepresentationalType.REPRESENTATION);
            fundingFileRepository.save(fundingService.toFundingFileEntity((fileDTOS.));
        }
        fundingService.fundingRegister(fundingDTO);
        return "funding/funding-initial-info";}

    @GetMapping("funding-gift")
    public String fundingGiftForm() {return "funding/funding-gift.html";}

    @PostMapping("funding-gift")
    public String fundingGift(FundingItemDTO fundingItemDTO){
        fundingItemService.ItemSave(fundingItemDTO);
        return "funding/funding-gift";
    }

//    @GetMapping("funding-list")
//    public String fundinglist() {return "funding/funding-list.html";}

    @GetMapping("funding")
    public String goToFundingList(Model model, Long fundingId){
            model.addAttribute("count", fundingService.getFundingCount(fundingId));

        return "/funding/funding-list";
    }

    @GetMapping("funding-list")
    @ResponseBody
    public Slice<FundingDTO> getFundingList(@RequestParam(defaultValue = "0", name = "page") int page) {
    PageRequest pageRequest = PageRequest.of(page, 12);

    return fundingService.getFundingList(pageRequest);

    }

//    @GetMapping("funding-payment")
//    public String fundingPayment() {return "funding/funding-payment.html";}

    @GetMapping("funding-payment")
    public String fundingPayment(Model model, Long memberId, Long fundingId) {
        MemberDTO memberDTO = memberService.getMemberById(memberId);
        FundingDTO fundingDTO = fundingService.getFundingDetail(fundingId);

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("fundingDTO", fundingDTO);


        return "funding/funding-payment";}

    @GetMapping("funding-plan")
    public String fundingPlan() {return "funding/funding-plan.html";}

    @GetMapping("funding-plan-main")
    public String fundingPlanMain() {return "funding/funding-plan-main.html";}

    @GetMapping("funding-project-plan")
    public String fundingProjectPlanForm() {return "funding/funding-project-plan";}

   /* @PostMapping("funding-project-plan")
    public String fundingProjectPlan(Long fundingId, int fundingTargetPrice, LocalDateTime fundingStartDate, LocalDateTime fundingEndDate) {
        fundingService.toFundingEntity(fundingService.updateFundingPlan())

        return "funding/funding-project-plan";}*/



// 후원자 수
     @GetMapping("funding-result")
    public String fundingResult() {return "funding/funding-result.html";}

    @GetMapping("funding-start")
    public String fundingStart() {return "funding/funding-start.html";}

//    @GetMapping("funding-topContent")
//    public String fundingTopContent() {return "funding/funding-topContent.html";}
}
